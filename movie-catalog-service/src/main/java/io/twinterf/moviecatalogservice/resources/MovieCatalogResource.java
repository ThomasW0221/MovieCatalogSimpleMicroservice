package io.twinterf.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.twinterf.moviecatalogservice.models.CatalogItem;
import io.twinterf.moviecatalogservice.models.Movie;
import io.twinterf.moviecatalogservice.models.Rating;
import io.twinterf.moviecatalogservice.models.UserRating;
import io.twinterf.moviecatalogservice.services.MovieInfo;
import io.twinterf.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public MovieInfo movieInfo;

    @Autowired
    public UserRatingInfo userRatingInfo;

    /*@Autowired
    private WebClient.Builder webClientBuilder;*/

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId){

        // get all rated movie ids
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getRatings().stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

}
