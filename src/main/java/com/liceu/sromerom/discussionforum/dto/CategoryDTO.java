package com.liceu.sromerom.discussionforum.dto;

import java.util.List;

public class CategoryDTO {
    private String color;
    private String description;
    private List<Long> moderators;
    private String slug;
    private String title;
    private Long _id;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getModerators() {
        return moderators;
    }

    public void setModerators(List<Long> moderators) {
        this.moderators = moderators;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
