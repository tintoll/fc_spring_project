package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User autheticate(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException());

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        return user;
    }
}
