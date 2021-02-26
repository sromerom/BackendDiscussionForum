package com.liceu.sromerom.discussionforum.dto;


import java.time.LocalDateTime;

public class TopicDTO {
    private Long category;
    private String content;
    private LocalDateTime createdAt;
    private Long _id;
    private Integer numberOfReplies;
    private String title;
    private LocalDateTime updatedAt;
    private UserDTO user;
    private Integer views;


    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Integer getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(Integer numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
                "category=" + category +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", _id=" + _id +
                ", numberOfReplies=" + numberOfReplies +
                ", title='" + title + '\'' +
                ", updatedAt=" + updatedAt +
                ", user={" + user.get_id() + ", " + user.getEmail() + ", " + user.getEmail() + ", " + user.getAvatarUrl() + ", " + user.getPermissions() + "}" +
                ", views=" + views +
                '}';
    }
}
