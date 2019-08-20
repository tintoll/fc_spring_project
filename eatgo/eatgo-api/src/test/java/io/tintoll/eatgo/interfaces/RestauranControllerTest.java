package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.RestaurantService;
import io.tintoll.eatgo.domain.MenuItem;
import io.tintoll.eatgo.domain.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 스프링을 이용해서 테스트를 진행한다.
@WebMvcTest(RestauranController.class) // RestauranController를 테스트한다고 명시한다.
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
        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant(1004L, "Bob zip", "Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));
        Restaurant restaurant2 = new Restaurant(2020L, "Cyber food", "Seoul");

        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurantById(2020L)).willReturn(restaurant2);


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
                ));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2020")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Cyber food\"")
                ));
    }


    @Test
    public void create() throws Exception {

        mvc.perform(post("/restaurants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}")) // json 형식으로 같을 넣어줌.
                .andExpect(status().isCreated()) // 201을 리턴하도록
                // .andExpect(header().string("location","/restaurants/1234")) // 헤더 정보에 location 확인
                .andExpect(content().string("{}"));

        // url을 호출한다음 아래 작업이 진행되게 하기 위해서 verify()를 이용한다.
        verify(restaurantService).addRestaurants(any()); // 어떤객체라도 들어오면 실행 했다는 것을 확인하기 위해 any()를 사용
    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                    .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\",\"address\":\"Updated Address\"}"))
                .andExpect(status().isOk());


        verify(restaurantService).updateRestaurant(1004L, "Updated Name", "Updated Address");
    }

}