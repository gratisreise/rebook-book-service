package com.example.rebookbookservice.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.exception.CDuplicatedDataException;
import com.example.rebookbookservice.feigns.UserClient;
import com.example.rebookbookservice.model.BookRequest;
import com.example.rebookbookservice.model.BookResponse;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.entity.Outbox;
import com.example.rebookbookservice.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.model.message.NotificationBookMessage;
import com.example.rebookbookservice.model.naver.Item;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.repository.BookMarkRepository;
import com.example.rebookbookservice.repository.BookRepository;
import com.example.rebookbookservice.repository.OutBoxRepository;
import com.example.rebookbookservice.utils.NotificationPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ApiService apiService;
    private final BookReader bookReader;
    private final UserClient userClient;
    private final BookMarkRepository bookMarkRepository;
    private final ObjectMapper objectMapper;
    private final OutBoxRepository outBoxRepository;

    public NaverBooksResponse searchNaverBooks(String keyword) {
        NaverBooksResponse response = apiService.searchBooks(keyword);
        List<Item> filteredItems = response.getItems().stream()
            .filter(item -> !bookReader.existsByIsbn(item.getIsbn()))
            .toList();
        response.setItems(filteredItems);
        return response;
    }

    @Transactional
    public void postBook(BookRequest request) throws JsonProcessingException {
        log.info("request {}", request.toString());
        if(bookRepository.existsByIsbn(request.getIsbn())){
            log.info("Book already exists");
            throw new CDuplicatedDataException("Duplicate BookInfo");
        }

        String category = apiService.getCategory(request.getTitle());
        LocalDate publishedDate = LocalDate.parse(request.getPublishedDate(), DateTimeFormatter.BASIC_ISO_DATE);
        Book book = new Book(request, category, publishedDate);

        Book postedBook =  bookRepository.save(book);

        //outbox 저장
        saveOutBox(category, postedBook);
    }

    private void saveOutBox(String category, Book postedBook) throws JsonProcessingException {
        String message = String.format("%s 카테고리에 새로운 도서가 등록되었습니다.", category);
        NotificationBookMessage notificationBookMessage =
            new NotificationBookMessage(postedBook.getId(), category, message);
        String payload  = objectMapper.writeValueAsString(notificationBookMessage);
        Outbox outBox = new Outbox();
        outBox.setPayload(payload);
        outBoxRepository.save(outBox);
    }

    public PageResponse<BookResponse> searchBooks(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContaining(keyword, pageable);
        Page<BookResponse> response = books.map(BookResponse::new);
        return new PageResponse<>(response);
    }

    public BookResponse getBook(String userId, Long bookId) {
        BookResponse response = new BookResponse(bookReader.readBookById(bookId));
        return checkMarking(response, userId);
    }

    public List<Long> getRecommendedBookIds(String userId) {
        log.info("before client");
        List<String> categories = userClient.getFavoriteCategories(userId);
        log.info("after client");
        log.info("getRecommendedBookIds categories: {}", categories.toString());
        List<Book> books = bookReader.readBookByCategoryIn(categories);
        return books.stream().map(Book::getId).toList();
    }
    private BookResponse checkMarking(BookResponse res, String userId){
        long bookId = res.getBookId();
        BookMarkId bookMarkId = new BookMarkId(bookId, userId);
        if(bookMarkRepository.existsByBookMarkId(bookMarkId)){
            res.setMarked(true);
        };
        return res;
    }

    public PageResponse<BookResponse> getBooks(String userId, Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        Page<BookResponse> responses = books.map(BookResponse::new)
            .map(res -> checkMarking(res, userId));
        return new PageResponse<>(responses);
    }


    public List<BookResponse> getRecommendedBooks(String userId) {
        List<String> categories = userClient.getFavoriteCategories(userId);
        List<Book> books = bookRepository.findTop5ByCategoryIn(categories);
        return books.stream().map(BookResponse::new).toList();
    }
}
