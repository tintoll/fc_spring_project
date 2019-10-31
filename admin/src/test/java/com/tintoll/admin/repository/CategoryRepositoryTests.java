package com.tintoll.admin.repository;

import com.tintoll.admin.AdminApplicationTests;
import com.tintoll.admin.model.entity.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CategoryRepositoryTests extends AdminApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        Category category = new Category();
        category.setType("노트북");

        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy("AdminServer");

        Category newCategory = categoryRepository.save(category);

        assertNotNull(newCategory);

    }
}