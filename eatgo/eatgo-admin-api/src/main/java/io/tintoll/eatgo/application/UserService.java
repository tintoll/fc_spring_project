package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User addUser(String email, String name) {
        return userRepository.save(User.builder().email(email).name(name).build());
    }

    public User updateUser(Long id, String email, String name, Long level) {

        User user = userRepository.findById(id).orElse(null);
        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);

        return user;
    }
}
