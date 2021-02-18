package com.liceu.sromerom.discussionforum.entities;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String avatarUrl;

    //@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String role;

    //Relationship User-Category (N-M)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "moderator",
            joinColumns = @JoinColumn(name = "user_userid"),
            inverseJoinColumns = @JoinColumn(name = "category_categoryid"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Category> moderatedCategories;


    //Relationship User-Topic (1-N)
    @OneToMany(mappedBy = "topicOwner", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Topic> topics;

    //Relationship User-Reply (1-N)
    @OneToMany(mappedBy = "replyOwner", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Reply> replies;

    public Long getId() {
        return id;
    }

    public void setId(Long userid) {
        this.id = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatar) {
        this.avatarUrl = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Category> getModeratedCategories() {
        return moderatedCategories;
    }

    public void setModeratedCategories(Set<Category> moderatedCategories) {
        this.moderatedCategories = moderatedCategories;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Reply> getReplies() {
        return replies;
    }

    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatarUrl + '\'' +
                ", role=" + role +
                ", moderatedCategories=" + moderatedCategories +
                ", topics=" + topics +
                ", replies=" + replies +
                '}';
    }
}
