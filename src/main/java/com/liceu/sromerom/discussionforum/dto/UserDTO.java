package com.liceu.sromerom.discussionforum.dto;


import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.entities.User;
import net.minidev.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class UserDTO {

    private Long _id;
    private String avatarUrl;
    private String email;
    private String name;
    private String role;
    private JSONObject permissions;

    public UserDTO() {
        this.permissions = new JSONObject();
    }

    public void completePermissions(List<Category> categoryList) {
        String[] defaultRoot = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete"};
        String[] adminRoot = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
        String [] categoriesAdmin = new String[]{"categories_topics:write", "categories_topics:delete", "categories_replies:write", "categories_replies:delete"};
        JSONObject categoryPermission = new JSONObject();
            switch(this.role) {
                case "moderator":
                    List<String> listCategorySlugsOwner = new ArrayList<>();

                    for (Category category: categoryList) {
                        for (User user: category.getModerators()) {
                            if(user.get_id().equals(this._id)) {
                                listCategorySlugsOwner.add(category.getSlug());
                            }
                        }
                    }

                    listCategorySlugsOwner.forEach(slug -> {
                        categoryPermission.put(slug, categoriesAdmin);
                    });
                    this.permissions.put("categories", categoryPermission);
                    this.permissions.put("root", defaultRoot);
                    break;
                case "admin":
                    List<String> listCategorySlugs = categoryList.stream().map(Category::getSlug).collect(Collectors.toList());
                    listCategorySlugs.forEach(slug -> categoryPermission.put(slug, categoriesAdmin));

                    this.permissions.put("categories", categoryPermission);
                    this.permissions.put("root", adminRoot);
                    break;
                default:
                    this.permissions.put("categories", "");
                    this.permissions.put("root", defaultRoot);
            }
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JSONObject getPermissions() {
        return permissions;
    }

    public void setPermissions(JSONObject permissions) {
        this.permissions = permissions;
    }
}
