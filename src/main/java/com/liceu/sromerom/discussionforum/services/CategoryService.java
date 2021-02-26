package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.CategoryDTO;
import com.liceu.sromerom.discussionforum.entities.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long categoryid);

    Category findBySlug(String slug);
    boolean existsCategoryBySlug(String slug);

    Category createCategory(CategoryDTO category);

    Category editCategory(String slug, CategoryDTO category);

    boolean deleteCategory(String slug);

    boolean userCanCRUDCategory(Map<String, Claim> userClaim);

    boolean userHavePermissionInCategory(Map<String, Claim> userClaim, String slug);
}
