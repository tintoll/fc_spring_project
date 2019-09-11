package io.tintoll.eatgo.domain;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(Long id) {
        super("not Found Restaurant " + id);
    }
}
