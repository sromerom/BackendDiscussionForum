package com.liceu.sromerom.discussionforum.services;


import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> findAll();
    List<Topic> findByCategory(String slug);
    Topic findTopicById(Long topicid);
    Topic createTopic(String email, String payload);

    Topic editTopic(Long topicid, String payload);

    boolean deleteTopic(Long topicid);

    void newView(Long topicid);
    boolean existsTopic(Long topicid);
}
