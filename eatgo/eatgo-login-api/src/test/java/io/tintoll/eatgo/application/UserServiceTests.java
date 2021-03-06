package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTests {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void autheticateWithValidAttribute() {
        String email = "tester@exam.com";
        String password = "test";

        User mockUser = User.builder().email(email).password(password).build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.autheticate(email,password);

        assertThat(user.getEmail(), is(email));

    }

    @Test(expected = EmailNotExistedException.class)
    public void autheticateWithEmailNotExisted() {
        String email = "x@exam.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        userService.autheticate(email,password);
    }

    @Test(expected = PasswordWrongException.class)
    public void autheticateWithPassWrongExpcetion () {
        String email = "tester@exam.com";
        String password = "x";

        User mockUser = User.builder().email(email).password(password).build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        userService.autheticate(email,password);

    }
}