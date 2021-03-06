package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.CategoryRepository;
import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@exam.com")
                .name("테스터")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getName(), is("테스터"));

    }

    @Test
    public void addUser() {

        String email = "admin@exam.com";
        String name = "Administrator";
        User mockUser = User.builder().email(email).name(name).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        verify(userRepository).save(any());

        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser() {
        Long id = 1L;
        String email = "admin@exam.com";
        String name = "SuperMan";
        Long level = 100L;

        User mockUser = User.builder().id(id).email(email).name("Administrator").level(1L).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.isAdmin(),is(true));
        assertThat(user.getName(),is("SuperMan"));

    }

    @Test
    public void deactiveUser() {

        Long id = 1L;
        String email = "admin@exam.com";

        User mockUser = User.builder().id(id).email(email).name("Administrator").level(1L).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deactivateUser(1L);

        verify(userRepository).findById(1L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActivate(), is(false));
    }
}