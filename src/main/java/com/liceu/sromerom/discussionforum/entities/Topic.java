package com.liceu.sromerom.discussionforum.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicid;

    @Column(nullable = false)
    private String title;
    private String content;

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    //Relationship Topic-Category (N-1)
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    //Relationship Topic-User (N-1)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User topicOwner;

    //Relationship Topic-Reply (1-N)
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Reply> replies;

    public Long getTopicid() {
        return topicid;
    }

    public void setTopicid(Long topicid) {
        this.topicid = topicid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getTopicOwner() {
        return topicOwner;
    }

    public void setTopicOwner(User topicOwner) {
        this.topicOwner = topicOwner;
    }

    public Set<Reply> getReplies() {
        return replies;
    }

    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicid=" + topicid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", category=" + category +
                ", topicOwner=" + topicOwner +
                ", replies=" + replies +
                '}';
    }
}
