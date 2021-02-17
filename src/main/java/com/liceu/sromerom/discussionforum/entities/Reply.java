package com.liceu.sromerom.discussionforum.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyid;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    //Relationship reply-User (N-1)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User replyOwner;

    //Relationship reply-Topic (N-1)
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    public Long getReplyid() {
        return replyid;
    }

    public void setReplyid(Long replyid) {
        this.replyid = replyid;
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

    public User getReplyOwner() {
        return replyOwner;
    }

    public void setReplyOwner(User replyOwner) {
        this.replyOwner = replyOwner;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replyid=" + replyid +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", replyOwner=" + replyOwner +
                ", topic=" + topic +
                '}';
    }
}
