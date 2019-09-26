package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.Review;
import io.tintoll.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Long restaurantId, Review resource) {
        resource.setRestaurantId(restaurantId);
        return reviewRepository.save(resource);
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}
