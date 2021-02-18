package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.services.CategoryService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriesController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> categories() {
        List<Category> result = categoryService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<?> getCategory(@PathVariable String slug) {
        if (categoryService.existsCategoryBySlug(slug)) {
            return ResponseEntity.ok(categoryService.findBySlug(slug));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<?> postCategories(@RequestBody Category newCategory) {
        System.out.println(newCategory);
        Category categoryCreated = categoryService.createCategory(newCategory);
        String message;
        JSONObject json = new JSONObject();
        if (categoryCreated != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreated);
        } else {
            json.put("message", "error");
            message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PutMapping("/categories/{slug}")
    public ResponseEntity<?> putCategories(@RequestBody Category modifyCategory, @PathVariable String slug) {
        if (categoryService.existsCategoryBySlug(slug)) {
            return ResponseEntity.ok(categoryService.editCategory(slug, modifyCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categories/{slug}")
    public ResponseEntity<?> deleteCategories(@PathVariable String slug) {
        if (categoryService.existsCategoryBySlug(slug)) {
            return ResponseEntity.ok(categoryService.deleteCategory(slug));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
