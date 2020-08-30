package io.twinterf.ratingsdataservice.resources;

import io.twinterf.ratingsdataservice.models.Rating;
import io.twinterf.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId){
        return new Rating(movieId, 4);
    }

    @GetMapping("users/{userId}")
    public UserRating getUserRatings(@PathVariable String userId){
         List<Rating> ratings = Arrays.asList(
                new Rating("100", 4),
                new Rating("200", 3)
        );
        var userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(ratings);
        return userRating;
    }
}
