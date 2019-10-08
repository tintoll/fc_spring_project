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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void create() throws Exception {


        String email = "tester@exam.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder().email(email).name(name).password(password).build();

        given(userService.registerUser(email, name, password)).willReturn(mockUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"tester@exam.com\", \"name\" : \"Tester\", \"password\" : \"test\"}"))
                .andExpect(status().isCreated());

        verify(userService).registerUser(eq(email),eq(name),eq(password));
    }
}