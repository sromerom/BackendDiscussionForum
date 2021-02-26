package com.liceu.sromerom.discussionforum.services;


import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.TopicDTORequest;
import com.liceu.sromerom.discussionforum.entities.Topic;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Topic> findAll();
    List<Topic> findByCategory(String slug);
    Topic findTopicById(Long topicid);
    Topic createTopic(String email, TopicDTORequest topicDTORequest);

    Topic editTopic(Long topicid, TopicDTORequest topicDTORequest);

    boolean deleteTopic(Long topicid);

    void newView(Long topicid);
    boolean existsTopic(Long topicid);

    boolean userCanCRUDTopics(Long topicid, Map<String, Claim> userClaim);

}
