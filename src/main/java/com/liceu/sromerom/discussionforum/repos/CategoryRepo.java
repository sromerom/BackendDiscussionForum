package com.liceu.sromerom.discussionforum.repos;

import com.liceu.sromerom.discussionforum.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);
    Category findBySlug(String slug);
}
