package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.JsonObject;
import com.liceu.sromerom.discussionforum.dto.CategoryDTO;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        return optional.orElse(null);
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

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        //slug
        //color
        Category category = new Category();
        String theoricalSlug = category.getTitle().toLowerCase().replace(" ", "-");
        int aux = 1;
        while (categoryRepo.existsBySlug(theoricalSlug)) {
            theoricalSlug = category.getTitle().toLowerCase().replace(" ", "-") + aux;
            aux++;
        }

        category.setSlug(theoricalSlug);
        category.setColor(generateRandomColor());

       return categoryRepo.save(category);

    }

    @Override
    public Category editCategory(String slug, CategoryDTO categoryDTO) {

        Category toEdit = categoryRepo.findBySlug(slug);
        toEdit.setTitle(categoryDTO.getTitle());
        toEdit.setDescription(categoryDTO.getDescription());
        return categoryRepo.save(toEdit);
    }

    @Override
    public boolean deleteCategory(String slug) {
        Category categoryToDelete = categoryRepo.findBySlug(slug);
        categoryRepo.deleteById(categoryToDelete.get_id());
        if (categoryRepo.existsBySlug(slug)) return false;
        return true;
    }

    @Override
    public boolean userCanCRUDCategory(Map<String, Claim> userClaim) {
        System.out.println("Entramos a comprobar!!");
        System.out.println("inService: " + userClaim.toString());
        String role = userClaim.get("role").asString();
        JSONObject permissions = userClaim.get("permissions").as(JSONObject.class);
        List<String> rootList =  (List<String>) permissions.get("root");
        boolean canWrite = rootList.stream().anyMatch(root -> root.equals("categories:write"));
        boolean canDelete = rootList.stream().anyMatch(root -> root.equals("categories:delete"));

        return canWrite && canDelete && role.equals("admin");
    }

    @Override
    public boolean userHavePermissionInCategory(Map<String, Claim> userClaim, String slug) {
        String role = userClaim.get("role").asString();
        JSONObject permissions = userClaim.get("permissions").as(JSONObject.class);
        if (permissions.get("categories").equals("") || permissions.get("categories") == null || permissions.size() == 0 || role.equals("user")) return false;
        Map<String, String> categoriesMap = (Map<String, String>) permissions.get("categories");
        boolean permissionInCurrentCategory = categoriesMap.containsKey(slug);
        return permissionInCurrentCategory && role.equals("admin");
    }

    private String generateRandomColor() {
        return "hsl(" + Math.round(Math.random() * 360) + ", 50%, 50%)";
    }
}
