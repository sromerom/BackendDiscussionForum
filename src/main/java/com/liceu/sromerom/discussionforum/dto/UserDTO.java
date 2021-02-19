package com.liceu.sromerom.discussionforum.dto;


import net.minidev.json.JSONObject;

public class UserDTO {

    private Long _id;
    private String avatarUrl;
    private String email;
    private String name;
    private String role;
    private JSONObject permissions;

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
