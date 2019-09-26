package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;


    // 모든 테스트를 실행전에 먼저 실행해주는 부분이다.
    @Before
    public void setUp() {
        // 현재 Spring 테스트가 아니기 때문에 의존관계를 생성해줘야 한다.
        // 서비스는 spring에 상관없이 해당 로직에만 집중하여 작업을 하면 된다.
//        restaurantRepository = new RestaurantRepositoryImpl();
//        menuItemRepository = new MenuItemRepositoryImpl();

        // @Mock를 초기화해준다.
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        assertThat(restaurants.get(0).getId(), is(1004L));
    }

    @Test
    public void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurantById(1004L);
        assertThat(restaurant.getId(), is(1004L));

    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithoutId() {
        Restaurant restaurant = restaurantService.getRestaurantById(404L);
    }

    @Test
    public void addRestaurant() {
        /*
            restaurantRepository.save(any()) 라는 행위가 일어났을때
            will 에 대한 부분이 넘어오는데 그 값을 변경하여 리턴해준다.
        */
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();
        
        Restaurant created = restaurantService.addRestaurants(restaurant);
        assertThat(created.getId(), is(1234L));
    }


    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("BeRyong")
                .address("Busan")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        // 실제로 restaurant가 변경되서 나와야된다.
        restaurantService.updateRestaurant(1004L, "Updated Name", "Updated Address");

        assertThat(restaurant.getName(), is("Updated Name"));
    }

}