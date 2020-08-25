package io.twinterf.moviecatalogservice.resources;

import io.twinterf.moviecatalogservice.models.CatalogItem;
import io.twinterf.moviecatalogservice.models.Movie;
import io.twinterf.moviecatalogservice.models.Rating;
import io.twinterf.moviecatalogservice.models.UserRating;
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

    /*@Autowired
    private WebClient.Builder webClientBuilder;*/

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId){

        // get all rated movie ids
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

         return ratings.getUserRating().stream().map(rating -> {
             // for each movie id, call movie info service and get details
             Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
             // put them all together
             return new CatalogItem(movie.getName(), "Desc", rating.getRating());
         }).collect(Collectors.toList());
    }
}
