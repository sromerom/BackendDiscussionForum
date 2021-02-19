package com.liceu.sromerom.discussionforum.dto;

import java.time.LocalDateTime;

public class ReplyDTO {
    private String content;
    private LocalDateTime createdAt;
    private Long topic;
    private LocalDateTime updatedAt;
    private UserDTO user;
    private Long _id;

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

    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
