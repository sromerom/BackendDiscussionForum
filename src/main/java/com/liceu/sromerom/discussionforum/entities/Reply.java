package com.liceu.sromerom.discussionforum.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "_id")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    //Relationship reply-User (N-1)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Relationship reply-Topic (N-1)
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;


    public Long get_id() {
        return _id;
    }

    public void set_id(Long id) {
        this._id = id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //@JsonBackReference(value = "replyOwner")
    public User getUser() {
        return user;
    }

    public void setUser(User replyOwner) {
        this.user = replyOwner;
    }

    //@JsonBackReference
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
