package com.liceu.sromerom.discussionforum.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "_id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false, unique = true)
    private String slug;
    private String title;
    private String description;
    private String color;

    //Relationship Category-User (N-M)
    @ManyToMany(mappedBy = "moderatedCategories", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> moderators;

    //Relationship Category-Topic (1-N)
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @net.minidev.json.annotate.JsonIgnore
    private Set<Topic> topics;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long id) {
        this._id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //@JsonManagedReference
    public Set<User> getModerators() {
        return moderators;
    }

    public void setModerators(Set<User> moderators) {
        this.moderators = moderators;
    }

    //@JsonManagedReference
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}
