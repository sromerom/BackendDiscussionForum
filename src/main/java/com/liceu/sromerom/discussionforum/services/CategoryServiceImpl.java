package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.CategoryDTO;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

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
        return categoryRepo.findBySlug(slug);
    }

    @Override
    public boolean existsCategoryBySlug(String slug) {
        return categoryRepo.existsBySlug(slug);
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        String slug = generateSlug(categoryDTO);

        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setSlug(slug);
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
        String role = userClaim.get("role").asString();
        JSONObject permissions = userClaim.get("permissions").as(JSONObject.class);
        List<String> rootList = (List<String>) permissions.get("root");
        boolean canWrite = rootList.stream().anyMatch(root -> root.equals("categories:write"));
        boolean canDelete = rootList.stream().anyMatch(root -> root.equals("categories:delete"));

        return canWrite && canDelete && role.equals("admin");
    }

    @Override
    public boolean userHavePermissionInCategory(Map<String, Claim> userClaim, String slug) {
        String role = userClaim.get("role").asString();
        JSONObject permissions = userClaim.get("permissions").as(JSONObject.class);
        if (permissions.get("categories").equals("") || permissions.get("categories") == null || permissions.size() == 0 || role.equals("user"))
            return false;
        Map<String, String> categoriesMap = (Map<String, String>) permissions.get("categories");
        boolean permissionInCurrentCategory = categoriesMap.containsKey(slug);
        return permissionInCurrentCategory && role.equals("admin");
    }

    private String generateSlug(CategoryDTO categoryDTO) {
        String theoricalSlug = categoryDTO.getTitle().toLowerCase().replace(" ", "-");
        int aux = 1;
        while (categoryRepo.existsBySlug(theoricalSlug)) {
            theoricalSlug = categoryDTO.getTitle().toLowerCase().replace(" ", "-") + aux;
            aux++;
        }

        return theoricalSlug;
    }

    private String generateRandomColor() {
        return "hsl(" + Math.round(Math.random() * 360) + ", 50%, 50%)";
    }
}
