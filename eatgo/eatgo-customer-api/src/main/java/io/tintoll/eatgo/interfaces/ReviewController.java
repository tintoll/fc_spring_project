package io.tintoll.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import io.tintoll.eatgo.application.ReviewService;
import io.tintoll.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();

        String name = (String)claims.get("name");
        String description = resource.getDescription();
        Integer score = resource.getScore();

        Review review = reviewService.addReview(restaurantId,name, score, description);
        String url = "/restaurants/"+restaurantId+"/reviews/"+review.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
