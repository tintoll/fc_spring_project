package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.Category;
import io.tintoll.eatgo.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        Category region = Category.builder().name(name).build();
        categoryRepository.save(region);
        return region;
    }
}
