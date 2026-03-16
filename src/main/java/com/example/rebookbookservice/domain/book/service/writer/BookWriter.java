package com.example.rebookbookservice.domain.book.service.writer;

import com.example.rebookbookservice.common.enums.Category;
import com.example.rebookbookservice.common.exception.BookException;
import com.example.rebookbookservice.domain.book.model.dto.request.BookRequest;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.repository.BookRepository;
import com.example.rebookbookservice.domain.book.service.AiService;
import com.example.rebookbookservice.external.rabbitmq.message.NotificationBookMessage;
import com.example.rebookbookservice.domain.outbox.Outbox;
import com.example.rebookbookservice.domain.outbox.OutBoxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookWriter {
    private final BookRepository bookRepository;
    private final OutBoxRepository outBoxRepository;
    private final ObjectMapper objectMapper;
    private final AiService aiService;

    public Book postBook(BookRequest request) throws JsonProcessingException {
        log.info("request {}", request);
        if (bookRepository.existsByIsbn(request.isbn())) {
            log.info("Book already exists");
            throw new BookException();
        }

        String categoryName = aiService.getCategory(request.title());
        Category category = Category.fromName(categoryName);
        LocalDate publishedDate = LocalDate.parse(request.publishedDate(), DateTimeFormatter.BASIC_ISO_DATE);
        Book book = request.toEntity(category, publishedDate);

        Book postedBook = bookRepository.save(book);
        saveOutBox(categoryName, postedBook);
        return postedBook;
    }

    public void updateBookRating(Book book, float rating) {
        book.setRating(rating);
    }

    private void saveOutBox(String category, Book postedBook) throws JsonProcessingException {
        String message = String.format("%s 카테고리에 새로운 도서가 등록되었습니다.", category);
        NotificationBookMessage notificationBookMessage =
            NotificationBookMessage.of(postedBook.getId(), category, message);
        String payload = objectMapper.writeValueAsString(notificationBookMessage);
        Outbox outBox = new Outbox();
        outBox.setPayload(payload);
        outBoxRepository.save(outBox);
    }
}
