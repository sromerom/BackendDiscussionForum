package com.liceu.sromerom.discussionforum.entities;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "_id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Topic> topicsCreated;

    //Relationship User-Reply (1-N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Reply> repliesCreated;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long id) {
        this._id = id;
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

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //@JsonManagedReference(value = "getModeratedCategories")
    public Set<Category> getModeratedCategories() {
        return moderatedCategories;
    }

    public void setModeratedCategories(Set<Category> moderatedCategories) {
        this.moderatedCategories = moderatedCategories;
    }


    //@JsonManagedReference(value = "topicOwner")
    public Set<Topic> getTopicsCreated() {
        return topicsCreated;
    }

    public void setTopicsCreated(Set<Topic> topics) {
        this.topicsCreated = topics;
    }

    //@JsonManagedReference(value = "replyOwner")
    public Set<Reply> getRepliesCreated() {
        return repliesCreated;
    }

    public void setRepliesCreated(Set<Reply> replies) {
        this.repliesCreated = replies;
    }
}
