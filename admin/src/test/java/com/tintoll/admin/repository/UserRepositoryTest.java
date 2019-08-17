package com.tintoll.admin.repository;

import com.tintoll.admin.AdminApplicationTests;
import com.tintoll.admin.model.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserRepositoryTest extends AdminApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setAccount("TestUser02");
        user.setEmail("TestUser02@gmail.com");
        user.setPhoneNumber("010-1111-1234");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser02");

        User newUser = userRepository.save(user);
        System.out.println("newUser :"+newUser );
    }

    public void read() {

    }

    public void update() {

    }

    public void delete() {

    }

}