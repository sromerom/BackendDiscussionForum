package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.entities.User;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long categoryid);

    Category findBySlug(String slug);
    boolean existsCategoryBySlug(String slug);

    Category createCategory(Category category);

    Category editCategory(String slug, Category category);

    boolean deleteCategory(String slug);
}
