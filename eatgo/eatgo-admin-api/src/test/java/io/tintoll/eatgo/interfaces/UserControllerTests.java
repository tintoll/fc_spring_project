package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.UserService;
import io.tintoll.eatgo.domain.User;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@exam.com")
                .name("테스터")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(mockUsers);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("테스터")));


    }


    @Test
    public void create() throws Exception {

        String email = "admin@exam.com";
        String name = "Administrator";

        User mockUser = User.builder().email(email).name(name).build();

        given(userService.addUser(email,name)).willReturn(mockUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@exam.com\",\"name\":\"Administrator\"}"))
           .andExpect(status().isCreated());

        verify(userService).addUser(email, name);
    }

    @Test
    public void updateUser() throws Exception {

        mvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@exam.com\",\"name\":\"Administrator\",\"level\":100}"))
                .andExpect(status().isOk());

        Long id = 1L;
        String email = "admin@exam.com";
        String name = "Administrator";
        Long level = 100L;

        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

}