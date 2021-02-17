package com.liceu.sromerom.discussionforum.repos;

import com.liceu.sromerom.discussionforum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Long> {
}
