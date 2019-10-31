package com.tintoll.admin.repository;

import com.tintoll.admin.AdminApplicationTests;
import com.tintoll.admin.model.entity.AdminUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AdminUserRepositoryTests extends AdminApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("test01");
        adminUser.setPassword("password");
        adminUser.setStatus("status");
        adminUser.setRole("ADMIN");

        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setCreatedBy("AdminServer");


        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        assertNotNull(newAdminUser);

    }
}