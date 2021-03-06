package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.EmailNotExistedException;
import io.tintoll.eatgo.application.PasswordWrongException;
import io.tintoll.eatgo.application.UserService;
import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithPublicUser() throws Exception {
        String email = "tester@exam.com";
        String password = "test";

        User mockUser = User.builder().id(1004L).level(1L).name("Tester").build();
        given(userService.autheticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(1004L,"Tester", null)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"tester@exam.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"));


        verify(userService).autheticate(eq(email), eq(password));
    }

    @Test
    public void createWithRestaurantOwner() throws Exception {
        String email = "tester@exam.com";
        String password = "test";

        User mockUser = User.builder().id(1004L).level(50L).restaurantId(369L).name("Tester").build();
        given(userService.autheticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(1004L,"Tester", 369L)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@exam.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"));


        verify(userService).autheticate(eq(email), eq(password));
    }

    @Test
    public void createWithPasswordWrongException() throws Exception {
        given(userService.autheticate("tester@exam.com","x")).willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@exam.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).autheticate(eq("tester@exam.com"),eq("x"));
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