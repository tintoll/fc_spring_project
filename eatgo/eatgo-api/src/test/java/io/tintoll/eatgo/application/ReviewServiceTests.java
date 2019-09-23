package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.Review;
import io.tintoll.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ReviewServiceTests {


    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void addReview() {
        Review review = Review.builder()
                .name("JOCKER")
                .score(3)
                .description("good")
                .build();

        reviewService.addReview(review);

        verify(reviewRepository).save(any());
    }

}