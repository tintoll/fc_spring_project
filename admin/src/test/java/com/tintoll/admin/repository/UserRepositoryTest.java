package com.tintoll.admin.repository;

import com.tintoll.admin.AdminApplicationTests;
import com.tintoll.admin.model.entity.Item;
import com.tintoll.admin.model.entity.User;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserRepositoryTest extends AdminApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setAccount("TestUser02");
        user.setPassword("password");
        user.setStatus("STATUS");

        user.setPhoneNumber("010-1111-1234");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser02");

        User newUser = userRepository.save(user);

        assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {
        Optional<User> user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-1234");
        // 값이 있으면
        user.ifPresent( selectUser -> {
            System.out.println("사용자 account : "+ selectUser.getAccount());
            System.out.println("사용자 폰넘버 : "+ selectUser.getPhoneNumber());

            selectUser.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("orderGroup : "+ orderGroup.toString());

                orderGroup.getOrderDetailList().stream().forEach(orderDetail -> {
                    System.out.println("orderDetail : "+orderDetail.toString());

                    System.out.println("item getBrandName : "+orderDetail.getItem().getBrandName());
                    System.out.println("partner getName : "+orderDetail.getItem().getPartner().getName());
                    System.out.println("category getTitle : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                });

            });


        });
    }

    @Test
    @Transactional
    public void update() {

        Optional<User> user = userRepository.findById(1L);
        // 값이 있으면
        user.ifPresent( selectUser -> {
            selectUser.setPhoneNumber("010-1234-1234");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("Modify");

            userRepository.save(selectUser);
        });

    }

    @Test
    @Transactional
    public void delete() {

        Optional<User> user = userRepository.findById(1L);

        Assert.assertTrue(user.isPresent());

        user.ifPresent( selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(2L);

        Assert.assertFalse(deleteUser.isPresent());
    }

}