package io.tintoll.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestaurantTests {

    @Test
    public void creation() {
        Restaurant restaurant = new Restaurant("Bob zip","Seoul");
        assertThat(restaurant.getName(), is("Bob zip"));
        assertThat(restaurant.getAddress(), is("Seoul"));
    }

    @Test
    public void infomation() {
        Restaurant restaurant = new Restaurant("Bob zip", "Seoul");
        assertThat(restaurant.getInfomation(), is("Bob zip in Seoul"));
    }

}