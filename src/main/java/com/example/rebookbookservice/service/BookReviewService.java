package com.example.rebookbookservice.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.exception.CDuplicatedDataException;
import com.example.rebookbookservice.exception.CUnauthrizedException;
import com.example.rebookbookservice.feigns.UserClient;
import com.example.rebookbookservice.model.BookReviewRequest;
import com.example.rebookbookservice.model.BookReviewResponse;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.entity.BookReview;
import com.example.rebookbookservice.model.user.AuthorsRequest;
import com.example.rebookbookservice.model.user.UserResponse;
import com.example.rebookbookservice.repository.BookReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private final UserClient userClient;

    @Transactional
    public void createBookReview(BookReviewRequest request, Long bookId, String userId) {
        if(bookReviewRepository.existsByBookIdAndUserId(bookId, userId)){
            throw new CDuplicatedDataException("BookReview already exist");
        }
        Book book = new Book(bookId);
        BookReview bookReview = new BookReview(request, book, userId);
        bookReviewRepository.save(bookReview);
    }

    @Transactional
    public void updateBookReview(BookReviewRequest request, Long reviewId) {
        BookReview review = bookReviewReader.readReview(reviewId);
        review.update(request);
    }

    @Transactional
    public void deleteBookReview(String userId, Long reviewId) {
        BookReview review = bookReviewReader.readReview(reviewId);
        if(!userId.equals(review.getUserId())) {
            log.error("Unauthorized to delete book review");
            throw new CUnauthrizedException("Unauthorized to delete book review");
        }
        bookReviewRepository.delete(review);
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

}
