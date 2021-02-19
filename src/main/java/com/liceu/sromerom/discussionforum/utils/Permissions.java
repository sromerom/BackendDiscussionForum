package com.liceu.sromerom.discussionforum.utils;

import com.liceu.sromerom.discussionforum.entities.Category;

import java.util.List;

public class Permissions {
    private List<Category> categories;
    private String [] root;

    public Permissions() {}

    public Permissions(List<Category> categories, String [] root) {
        this.categories = categories;
        this.root = root;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String[] getRoot() {
        return root;
    }

    public void setRoot(String[] root) {
        this.root = root;
    }
}
