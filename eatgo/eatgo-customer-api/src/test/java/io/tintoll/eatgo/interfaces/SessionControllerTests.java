package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.EmailNotExistedException;
import io.tintoll.eatgo.application.PasswordWrongException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        String email = "tester@exam.com";
        String password = "test";

        User mockUser = User.builder().password("ACCESSTOKE").build();
        given(userService.autheticate(email, password)).willReturn(mockUser);

        mvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"tester@exam.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKE\"}"));


        verify(userService).autheticate(eq(email), eq(password));
    }

    @Test
    public void createWithPasswordWrongException() throws Exception {
        given(userService.autheticate("tester@exam.com","x")).willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@exam.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).autheticate(eq("tester@exam.com"), eq("x"));
    }

    @Test
    public void createWithEmailNotExistedException() throws Exception {
        given(userService.autheticate("x@exam.com","test")).willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@exam.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).autheticate(eq("x@exam.com"), eq("test"));
    }

}