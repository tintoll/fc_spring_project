package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.RestaurantService;
import io.tintoll.eatgo.domain.MenuItem;
import io.tintoll.eatgo.domain.Restaurant;
import io.tintoll.eatgo.domain.RestaurantNotFoundException;
import io.tintoll.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 스프링을 이용해서 테스트를 진행한다.
@WebMvcTest(RestaurantController.class) // RestauranController를 테스트한다고 명시한다.
public class RestauranControllerTest {

    @Autowired
    private MockMvc mvc;

    // RestauranController에서 사용하는 객체의 의존성을 주입해주어야 한다.
    // 실제 구현체를 넣어줘야 에러가 나지 않는다.
//    @SpyBean(RestaurantRepositoryImpl.class)
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;

    // 가짜 객체를 넣어준다.
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        // 컨트롤러는 서비스가 어떻게 동작하는지 관심이 없고 활용하는 부분에 의의를 둠.
        // 가짜 데이터를 넣어서 확인을 하여준다.
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build());
        given(restaurantService.getRestaurants("Seoul")).willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailExisted() throws Exception {
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurant1.setMenuItems(Arrays.asList(MenuItem.builder()
                .name("Kimchi")
                .build()));
        restaurant1.setReviews(Arrays.asList(Review.builder()
                .name("JOCKER")
                .score(1)
                .description("bad!")
                .build()));

        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant1);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ))
                .andExpect(content().string(
                        containsString("bad!")
                ));
        
    }

    @Test
    public void detailNotExisted() throws Exception {

        /*
            /restaurants/404  API 로직안에서 restaurantService.getRestaurantById(404L) 를 호출하였을때
            어떠한 결과가 나오는지 정의를 해줘야한다.
         */
        given(restaurantService.getRestaurantById(404L)).willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));

    }

}