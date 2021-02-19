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

    @Override
    public Category createCategory(Category category) {
        //slug
        //color
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
    public Category editCategory(String slug, Category category) {

        Category toEdit = categoryRepo.findBySlug(slug);
        toEdit.setTitle(category.getTitle());
        toEdit.setDescription(category.getDescription());
        return categoryRepo.save(toEdit);
    }

    @Override
    public boolean deleteCategory(String slug) {
        Category categoryToDelete = categoryRepo.findBySlug(slug);
        categoryRepo.deleteById(categoryToDelete.get_id());
        if (categoryRepo.existsBySlug(slug)) return false;
        return true;
    }

    private String generateRandomColor() {
        return "hsl(" + Math.round(Math.random() * 360) + ", 50%, 50%)";
    }
}
