package com.liceu.sromerom.discussionforum.repos;

import com.liceu.sromerom.discussionforum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    List<Topic> findByCategory_Slug(String slug);
}
