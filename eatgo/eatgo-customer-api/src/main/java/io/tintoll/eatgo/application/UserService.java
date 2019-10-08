package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String name, String password) {

        User user = User.builder()
                        .email(email)
                        .name(name)
                        .password(password)
                        .level(1L)
                        .build();
        userRepository.save(user);

        return user;
    }
}
