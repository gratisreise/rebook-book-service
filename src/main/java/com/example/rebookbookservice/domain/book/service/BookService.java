package com.example.rebookbookservice.domain.book.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.clientfeign.user.UserClient;
import com.example.rebookbookservice.domain.book.model.BookRequest;
import com.example.rebookbookservice.domain.book.model.BookResponse;
import com.example.rebookbookservice.domain.book.model.Item;
import com.example.rebookbookservice.domain.book.model.NaverBooksResponse;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.Outbox;
import com.example.rebookbookservice.domain.book.repository.BookRepository;
import com.example.rebookbookservice.domain.bookmark.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.domain.bookmark.repository.BookMarkRepository;
import com.example.rebookbookservice.domain.outbox.repository.OutBoxRepository;
import com.example.rebookbookservice.exception.CDuplicatedDataException;
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
        List<Item> filteredItems = response.items().stream()
            .filter(item -> !bookReader.existsByIsbn(item.isbn()))
            .toList();
        return new NaverBooksResponse(
            response.lastBuildDate(),
            response.total(),
            response.start(),
            response.display(),
            filteredItems
        );
    }

    @Transactional
    public void postBook(BookRequest request) throws JsonProcessingException {
        log.info("request {}", request);
        if(bookRepository.existsByIsbn(request.isbn())){
            log.info("Book already exists");
            throw new CDuplicatedDataException("Duplicate BookInfo");
        }

        String category = apiService.getCategory(request.title());
        LocalDate publishedDate = LocalDate.parse(request.publishedDate(), DateTimeFormatter.BASIC_ISO_DATE);
        Book book = new Book(request, category, publishedDate);

        Book postedBook = bookRepository.save(book);

        saveOutBox(category, postedBook);
    }

    private void saveOutBox(String category, Book postedBook) throws JsonProcessingException {
        String message = String.format("%s 카테고리에 새로운 도서가 등록되었습니다.", category);
        com.example.rebookbookservice.domain.book.model.NotificationBookMessage notificationBookMessage =
            new com.example.rebookbookservice.domain.book.model.NotificationBookMessage(postedBook.getId(), category, message);
        String payload = objectMapper.writeValueAsString(notificationBookMessage);
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
        log.info("getRecommendedBookIds categories: {}", categories);
        List<Book> books = bookReader.readBookByCategoryIn(categories);
        return books.stream().map(Book::getId).toList();
    }

    private BookResponse checkMarking(BookResponse res, String userId){
        long bookId = res.bookId();
        BookMarkId bookMarkId = new BookMarkId(bookId, userId);
        if(bookMarkRepository.existsByBookMarkId(bookMarkId)){
            return res.withMarked(true);
        }
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
