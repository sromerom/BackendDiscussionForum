package com.liceu.sromerom.discussionforum.dto;

public class UserDTORequest {
    private String email;
    private String moderateCategory;
    private String name;
    private String password;
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModerateCategory() {
        return moderateCategory;
    }

    public void setModerateCategory(String moderateCategory) {
        this.moderateCategory = moderateCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
