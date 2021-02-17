package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long categoryid);

    Category findBySlug(String slug);
    boolean existsCategoryBySlug(String slug);
}
