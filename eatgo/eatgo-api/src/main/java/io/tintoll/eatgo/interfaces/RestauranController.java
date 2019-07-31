package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.RestaurantService;
import io.tintoll.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RestauranController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
     return restaurantService.getRestaurants();
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {

        String name = resource.getName();
        String address = resource.getAddress();

        Restaurant restaurant = new Restaurant( name, address);
        restaurantService.addRestaurants(restaurant);

        URI location = new URI("/restaurants/"+restaurant.getId());
        // ResponseEntity 를 이용하여 status()를 201로 보내준다.
        return ResponseEntity.created(location).body("{}");
    }
}
