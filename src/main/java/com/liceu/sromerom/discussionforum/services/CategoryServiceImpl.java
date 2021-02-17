package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();

    }

    @Override
    public Category findById(Long categoryid) {
        Optional<Category> optional = categoryRepo.findById(categoryid);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public Category findBySlug(String slug) {
        Category category = categoryRepo.findBySlug(slug);
        if (category != null) return category;
        return null;
    }

    @Override
    public boolean existsCategoryBySlug(String slug) {
        return categoryRepo.existsBySlug(slug);
    }
}
