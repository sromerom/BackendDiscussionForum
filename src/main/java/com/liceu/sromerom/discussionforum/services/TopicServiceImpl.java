package com.liceu.sromerom.discussionforum.services;

import com.google.gson.Gson;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import com.liceu.sromerom.discussionforum.repos.TopicRepo;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public List<Topic> findAll() {
        return topicRepo.findAll();
    }

    @Override
    public List<Topic> findByCategory(String slug) {
       return topicRepo.findByCategory_Slug(slug);
    }

    @Override
    public Topic findTopicById(Long topicid) {
        return topicRepo.findById(topicid).get();
    }

    @Override
    public Topic createTopic(String email, String payload) {
        Gson gson = new Gson();
        Map<String, String> payloadMap = gson.fromJson(payload, HashMap.class);

        Topic topicToCreate = new Topic();
        topicToCreate.setTitle(payloadMap.get("title"));
        topicToCreate.setContent(payloadMap.get("content"));
        topicToCreate.setViews(0);
        topicToCreate.setCreatedAt(LocalDateTime.now());
        topicToCreate.setUpdatedAt(LocalDateTime.now());
        topicToCreate.setCategory(categoryRepo.findBySlug(payloadMap.get("category")));
        topicToCreate.setUser(userRepo.findByEmail(email));

        return topicRepo.save(topicToCreate);
    }

    @Override
    public Topic editTopic(Long topicid, String payload) {
        Gson gson = new Gson();
        Map<String, String> payloadMap = gson.fromJson(payload, HashMap.class);
        Topic topicToEdit = topicRepo.findById(topicid).get();
        topicToEdit.setTitle(payloadMap.get("title"));
        topicToEdit.setContent(payloadMap.get("content"));
        topicToEdit.setCategory(categoryRepo.findBySlug(payloadMap.get("category")));
        return topicRepo.save(topicToEdit);
    }

    @Override
    public boolean deleteTopic(Long topicid) {
        if (existsTopic(topicid)) {
            topicRepo.deleteById(topicid);
            if (!existsTopic(topicid)) return true;
        }
        return false;
    }

    @Override
    public void newView(Long topicid) {
        Topic topicToAddNewView = topicRepo.findById(topicid).get();
        topicToAddNewView.setViews(topicToAddNewView.getViews() + 1);
        topicRepo.save(topicToAddNewView);
    }

    @Override
    public boolean existsTopic(Long topicid) {
        return topicRepo.existsById(topicid);
    }
}
