package com.example.rebookbookservice.domain.bookreview.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.clientfeign.user.UserClient;
import com.example.rebookbookservice.clientfeign.user.dto.request.AuthorsRequest;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.service.BookReader;
import com.example.rebookbookservice.domain.bookreview.model.BookReviewRequest;
import com.example.rebookbookservice.domain.bookreview.model.BookReviewResponse;
import com.example.rebookbookservice.domain.bookreview.model.entity.BookReview;
import com.example.rebookbookservice.domain.bookreview.repository.BookReviewRepository;
import com.example.rebookbookservice.exception.CDuplicatedDataException;
import com.example.rebookbookservice.exception.CUnauthrizedException;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewReader bookReviewReader;
    private final BookReader bookReader;
    private final UserClient userClient;

    @Transactional
    public void createBookReview(BookReviewRequest request, Long bookId, String userId) {

        if(bookReviewRepository.existsByBookIdAndUserId(bookId, userId)){
            throw new CDuplicatedDataException("BookReview already exist");
        }

        Book book = bookReader.readBookById(bookId);
        BookReview bookReview = new BookReview(request, book, userId);
        bookReviewRepository.save(bookReview);

        starRatingUpdate(bookId, book);
    }

    @Transactional
    public void updateBookReview(BookReviewRequest request, Long reviewId, Long bookId) {
        BookReview review = bookReviewReader.readReview(reviewId);
        review.update(request);
        Book book = bookReader.readBookById(bookId);
        starRatingUpdate(bookId, book);
    }

    @Transactional
    public void deleteBookReview(String userId, Long reviewId, Long bookId) {
        BookReview review = bookReviewReader.readReview(reviewId);
        if(!userId.equals(review.getUserId())) {
            log.error("Unauthorized to delete book review");
            throw new CUnauthrizedException("Unauthorized to delete book review");
        }
        bookReviewRepository.delete(review);
        Book book = bookReader.readBookById(bookId);
        starRatingUpdate(bookId, book);
    }

    public PageResponse<BookReviewResponse> getReviews(Long bookId, Pageable pageable) {
        Page<BookReview> reviews = bookReviewRepository.findByBookId(bookId, pageable);

        List<String> userIds = reviews.getContent().stream().map(BookReview::getUserId).toList();
        List<String> authors = userClient.getUser(new AuthorsRequest(userIds));
        List<BookReview> content = reviews.getContent();

        List<BookReviewResponse> responseList = getResponseList(content, authors);
        Page<BookReviewResponse> responsePage =
            new PageImpl<>(responseList, reviews.getPageable(), reviews.getTotalElements());
        return new PageResponse<>(responsePage);
    }

    private static List<BookReviewResponse> getResponseList(List<BookReview> content,
        List<String> authors) {
        return IntStream.range(0, content.size())
            .mapToObj(i -> {
                BookReview review = content.get(i);
                String author = authors.get(i);
                return new BookReviewResponse(review, author);
            }).toList();
    }

    private void starRatingUpdate(Long bookId, Book book) {
        float ratingAvg = bookReviewRepository.findAverageScoreByBookId(bookId);
        book.setRating(ratingAvg);
    }
}
