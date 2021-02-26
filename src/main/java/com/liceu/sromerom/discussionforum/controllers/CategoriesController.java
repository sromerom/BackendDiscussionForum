package com.liceu.sromerom.discussionforum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.CategoryDTO;
import com.liceu.sromerom.discussionforum.dto.converter.CategoryDTOConverter;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.services.CategoryService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CategoriesController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDTOConverter categoryDTOConverter;

    @GetMapping("/categories")
    public ResponseEntity<?> categories() {
        List<Category> result = categoryService.findAll();
        List<CategoryDTO> dtoList = result.stream()
                .map(categoryDTOConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<?> getCategory(@PathVariable String slug) {
        if (categoryService.existsCategoryBySlug(slug)) {
            return ResponseEntity.ok(categoryDTOConverter.convertToDTO(categoryService.findBySlug(slug)));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<?> postCategories(@RequestBody CategoryDTO newCategory, @RequestAttribute Map<String, Claim> user) {
        String message;
        JSONObject json = new JSONObject();
        if (categoryService.userCanCRUDCategory(user)) {
            Category categoryCreated = categoryService.createCategory(newCategory);
            if (categoryCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTOConverter.convertToDTO(categoryCreated));
            } else {
                json.put("message", "error");
                message = json.toJSONString();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PutMapping("/categories/{slug}")
    public ResponseEntity<?> putCategories(@RequestBody CategoryDTO modifyCategory, @PathVariable String slug, @RequestAttribute Map<String, Claim> user) {

        if (categoryService.userCanCRUDCategory(user) && categoryService.userHavePermissionInCategory(user, slug)) {
            if (categoryService.existsCategoryBySlug(slug)) {
                return ResponseEntity.ok(categoryDTOConverter.convertToDTO(categoryService.editCategory(slug, modifyCategory)));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @DeleteMapping("/categories/{slug}")
    public ResponseEntity<?> deleteCategories(@PathVariable String slug, @RequestAttribute Map<String, Claim> user) {

        if (categoryService.userCanCRUDCategory(user) && categoryService.userHavePermissionInCategory(user, slug)) {
            if (categoryService.existsCategoryBySlug(slug)) {
                return ResponseEntity.ok(categoryService.deleteCategory(slug));
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
