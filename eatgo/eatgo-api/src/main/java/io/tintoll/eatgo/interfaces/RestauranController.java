package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.domain.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestauranController {

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));
        restaurants.add(new Restaurant(2020L,"Cyber food","Seoul"));

        Restaurant restaurant = restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null); // 없으면 null을 리턴
                // .get();
        return restaurant;
    }
}
