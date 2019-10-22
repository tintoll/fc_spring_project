package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.Review;
import io.tintoll.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        Review mockReview = Review.builder().id(1L).build();
        given(reviewRepository.save(any())).willReturn(mockReview);

        Review review = reviewService.addReview(1L, "JOCKER", 3, "good");

        assertThat(review.getId(), is(1L));

        verify(reviewRepository).save(any());
    }

}