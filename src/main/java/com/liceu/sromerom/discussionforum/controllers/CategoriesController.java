package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> categories() {
        List<Category> result = categoryService.findAll();

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<?> getCategory(@PathVariable String slug) {
        if (categoryService.existsCategoryBySlug(slug)) {
            return ResponseEntity.ok(categoryService.findBySlug(slug));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
