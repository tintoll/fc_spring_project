package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {


    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow( () -> new RestaurantNotFoundException(id));
        return restaurant;
    }

    public Restaurant addRestaurants(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }


    @Transactional
    public Restaurant updateRestaurant(Long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInfomation(name, address);
        // 객체의 정보만 변경해주고 save하는 행위가 없지만
        // @Transitional을 해주면 트랜잭션 범위가 벗어나면 정보가 업데이트된다.
        return restaurant;
    }
}
