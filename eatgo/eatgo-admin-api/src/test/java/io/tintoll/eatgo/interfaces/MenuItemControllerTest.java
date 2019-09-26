package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.MenuItemService;
import io.tintoll.eatgo.domain.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void bulkUpdate() throws Exception {

        mvc.perform(patch("/restaurants/1/menuItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
           .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(eq(1L), any());
    }

    @Test
    public void list() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("kimchi").build());

        given(menuItemService.getMenuItems(1L)).willReturn(menuItems);

        mvc.perform(get("/restaurants/1/menuItems"))
                .andExpect(status().isOk())
                // 여러 리스트에 있는 데이터중 하나를 확인하기 위해서 org.hamcrest.Matchers.containsString 사용
                .andExpect(content().string(containsString("kimchi")));
    }

}