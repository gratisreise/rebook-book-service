package com.example.rebookbookservice.domain.book.service;

import com.example.rebookbookservice.clientfeign.user.UserClient;
import com.example.rebookbookservice.clientfeign.user.dto.request.AuthorsRequest;
import com.example.rebookbookservice.common.exception.BookException;
import com.example.rebookbookservice.domain.book.model.dto.request.BookRequest;
import com.example.rebookbookservice.domain.book.model.dto.request.BookReviewRequest;
import com.example.rebookbookservice.domain.book.model.dto.response.BookResponse;
import com.example.rebookbookservice.domain.book.model.dto.response.BookReviewResponse;
import com.example.rebookbookservice.domain.book.model.dto.response.ExtractedBookInfo;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.BookReview;
import com.example.rebookbookservice.domain.book.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.domain.book.repository.BookRepository;
import com.example.rebookbookservice.domain.book.repository.BookReviewRepository;
import com.example.rebookbookservice.domain.book.service.reader.BookMarkReader;
import com.example.rebookbookservice.domain.book.service.reader.BookReader;
import com.example.rebookbookservice.domain.book.service.writer.BookMarkWriter;
import com.example.rebookbookservice.domain.book.service.writer.BookReviewWriter;
import com.example.rebookbookservice.domain.book.service.writer.BookWriter;
import com.example.rebookbookservice.external.gemini.ImageSource;
import com.example.rebookbookservice.external.naverbooks.NaverBooksClient;
import com.example.rebookbookservice.external.naverbooks.NaverBooksResponse;
import com.example.rebookbookservice.external.naverbooks.NaverBooksResponse.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rebook.common.core.response.PageResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookService {
  private final BookRepository bookRepository;
  private final BookReviewRepository bookReviewRepository;
  private final BookReader bookReader;
  private final BookWriter bookWriter;
  private final BookMarkReader bookMarkReader;
  private final BookMarkWriter bookMarkWriter;
  private final BookReviewWriter bookReviewWriter;
  private final UserClient userClient;
  private final NaverBooksClient naverBooksClient;
  private final AiService aiService;

  // === Book ===

  public NaverBooksResponse searchNaverBooks(String keyword) {
    NaverBooksResponse response = naverBooksClient.searchBooksByTitle(keyword, 10);
    List<Item> filteredItems =
        response.items().stream().filter(item -> !bookReader.existsByIsbn(item.isbn())).toList();
    return new NaverBooksResponse(
        response.lastBuildDate(),
        response.total(),
        response.start(),
        response.display(),
        filteredItems);
  }

  @Transactional
  public void postBook(BookRequest request) throws JsonProcessingException {
    bookWriter.postBook(request);
  }

  public PageResponse<BookResponse> searchBooks(String keyword, Pageable pageable) {
    Page<Book> books = bookRepository.findByTitleContaining(keyword, pageable);
    Page<BookResponse> response = books.map(BookResponse::from);
    return PageResponse.from(response);
  }

  public BookResponse getBook(String userId, Long bookId) {
    BookResponse response = BookResponse.from(bookReader.readBookById(bookId));
    return checkMarking(response, userId);
  }

  public PageResponse<BookResponse> getBooks(String userId, Pageable pageable) {
    Page<Book> books = bookRepository.findAll(pageable);
    Page<BookResponse> responses =
        books.map(BookResponse::from).map(res -> checkMarking(res, userId));
    return PageResponse.from(responses);
  }

  public List<BookResponse> getRecommendedBooks(String userId) {
    List<String> categories = userClient.getFavoriteCategories(userId);
    List<Book> books = bookRepository.findTop5ByCategoryIn(categories);
    return books.stream().map(BookResponse::from).toList();
  }

  public List<Long> getRecommendedBookIds(String userId) {
    List<String> categories = userClient.getFavoriteCategories(userId);
    List<Book> books = bookReader.readBookByCategoryIn(categories);
    return books.stream().map(Book::getId).toList();
  }

  private BookResponse checkMarking(BookResponse res, String userId) {
    long bookId = res.bookId();
    BookMarkId bookMarkId = new BookMarkId(bookId, userId);
    if (bookMarkReader.existsByBookMarkId(bookMarkId)) {
      return res.withMarked(true);
    }
    return res;
  }

  // === BookMark Operations ===

  @Transactional
  public void markingToggle(String userId, Long bookId) {
    bookMarkWriter.markingToggle(userId, bookId);
  }

  public PageResponse<Book> getMarkedBooks(String userId, Pageable pageable) {
    return PageResponse.from(bookMarkReader.getMarkedBooks(userId, pageable));
  }

  // === BookReview ===

  @Transactional
  public void createBookReview(BookReviewRequest request, Long bookId, String userId) {
    bookReviewWriter.createBookReview(request, bookId, userId);
  }

  @Transactional
  public void updateBookReview(BookReviewRequest request, Long reviewId, Long bookId) {
    bookReviewWriter.updateBookReview(request, reviewId, bookId);
  }

  @Transactional
  public void deleteBookReview(String userId, Long reviewId, Long bookId) {
    bookReviewWriter.deleteBookReview(userId, reviewId, bookId);
  }

  public PageResponse<BookReviewResponse> getReviews(Long bookId, Pageable pageable) {
    Page<BookReview> reviews = bookReviewRepository.findByBookId(bookId, pageable);

    List<String> userIds = reviews.getContent().stream().map(BookReview::getUserId).toList();
    List<String> authors = userClient.getUser(new AuthorsRequest(userIds));
    List<BookReview> content = reviews.getContent();

    List<BookReviewResponse> responseList = getResponseList(content, authors);
    Page<BookReviewResponse> responsePage =
        new PageImpl<>(responseList, reviews.getPageable(), reviews.getTotalElements());
    return PageResponse.from(responsePage);
  }

  private static List<BookReviewResponse> getResponseList(
      List<BookReview> content, List<String> authors) {
    return IntStream.range(0, content.size())
        .mapToObj(
            i -> {
              BookReview review = content.get(i);
              String author = authors.get(i);
              return BookReviewResponse.from(review, author);
            })
        .toList();
  }

  // === Alarm  ===

  public List<String> getUserIdsByBookId(Long bookId) {
    return bookReader.getUserIdsByBookId(bookId);
  }

  // === Image Search ===

  @Transactional
  public List<BookResponse> searchBooksByImage(MultipartFile image, String userId)
      throws JsonProcessingException {

    // 1. 이미지 검증
    validateImage(image);

    // 2. Gemini로 이미지에서 도서명 기반 ISBN 후보 추출
    ImageSource imageSource;
    try {
      imageSource = ImageSource.of(image.getBytes(), image.getContentType());
    } catch (Exception e) {
      log.error("Failed to read image bytes", e);
      throw new BookException();
    }

    ExtractedBookInfo extractedInfo = aiService.extractBookInfoFromImage(List.of(imageSource));

    if (extractedInfo.books() == null || extractedInfo.books().isEmpty()) {
      return List.of();
    }

    // 3. 각 도서의 ISBN 후보군 처리
    List<Book> resultBooks = new ArrayList<>();
    for (ExtractedBookInfo.BookIsbnCandidate bookCandidate : extractedInfo.books()) {
      Book book = processBookByIsbnCandidates(bookCandidate.isbns());
      if (book != null) {
        resultBooks.add(book);
      }
    }

    // 4. 응답 변환
    return resultBooks.stream()
        .map(BookResponse::from)
        .map(res -> checkMarking(res, userId))
        .toList();
  }

  private void validateImage(MultipartFile image) {
    if (image.isEmpty()) {
      throw new BookException();
    }
    String contentType = image.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      throw new BookException();
    }
  }

  private Book processBookByIsbnCandidates(List<String> isbnCandidates) throws JsonProcessingException {
    // ISBN 후보군을 순차적으로 시도
    for (String isbn : isbnCandidates) {
      Book book = processBookByIsbn(isbn);
      if (book != null) {
        return book;
      }
    }
    return null;
  }

  private Book processBookByIsbn(String isbn) throws JsonProcessingException {
    // 1. 내부 DB 조회
    Optional<Book> existingBook = bookReader.findByIsbn(isbn);
    if (existingBook.isPresent()) {
      return existingBook.get();
    }

    // 2. Naver API ISBN 검색
    NaverBooksResponse naverResponse = naverBooksClient.searchBooksByIsbn(isbn, 1);
    if (naverResponse.items().isEmpty()) {
      return null; // ISBN으로 찾을 수 없음
    }

    // 3. 저장 후 반환
    Item item = naverResponse.items().get(0);
    if (bookReader.existsByIsbn(item.isbn())) {
      return bookReader.findByIsbn(item.isbn()).orElse(null);
    }

    BookRequest bookRequest = convertNaverItemToRequest(item);
    return bookWriter.saveBookFromNaver(bookRequest);
  }

  private BookRequest convertNaverItemToRequest(Item item) {
    return new BookRequest(
        item.title().replaceAll("<[^>]*>", ""),
        item.author(),
        item.publisher(),
        item.isbn(),
        item.description().replaceAll("<[^>]*>", ""),
        item.discount() != null ? Integer.parseInt(item.discount()) : 0,
        item.pubdate(),
        item.image());
  }
}
