package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findByEmail(email);
        if(existed.isPresent()) {
            throw new EmailExistedException(email);
        }

        String encodePassword = passwordEncoder.encode(password);

        User user = User.builder()
                        .email(email)
                        .name(name)
                        .password(encodePassword)
                        .level(1L)
                        .build();
        userRepository.save(user);

        return user;
    }

    public User autheticate(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException());

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        return user;
    }
}
