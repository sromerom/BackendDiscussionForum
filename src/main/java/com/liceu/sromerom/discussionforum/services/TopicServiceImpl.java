package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.TopicDTORequest;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import com.liceu.sromerom.discussionforum.repos.TopicRepo;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public List<Topic> findByCategory(String slug) {
        return topicRepo.findByCategory_Slug(slug);
    }

    @Override
    public Topic findTopicById(Long topicid) {
        return topicRepo.findById(topicid).get();
    }

    @Override
    public Topic createTopic(String email, TopicDTORequest topicDTORequest) {
        Topic topicToCreate = new Topic();
        topicToCreate.setTitle(topicDTORequest.getTitle());
        topicToCreate.setContent(topicDTORequest.getContent());
        topicToCreate.setViews(0);
        topicToCreate.setCreatedAt(LocalDateTime.now());
        topicToCreate.setUpdatedAt(LocalDateTime.now());
        topicToCreate.setCategory(categoryRepo.findBySlug(topicDTORequest.getCategory()));
        topicToCreate.setUser(userRepo.findByEmail(email));
        return topicRepo.save(topicToCreate);
    }

    @Override
    public Topic editTopic(Long topicid, TopicDTORequest topicDTORequest) {
        Optional<Topic> exists = topicRepo.findById(topicid);
        if (exists.isPresent()) {
            Topic topicToEdit = exists.get();
            topicToEdit.setTitle(topicDTORequest.getTitle());
            topicToEdit.setContent(topicDTORequest.getContent());
            topicToEdit.setCategory(categoryRepo.findBySlug(topicDTORequest.getCategory()));
            return topicRepo.save(topicToEdit);
        }
        return null;
    }

    @Override
    public boolean deleteTopic(Long topicid) {
        if (existsTopic(topicid)) {
            topicRepo.deleteById(topicid);
            return !existsTopic(topicid);
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

    @Override
    public boolean userCanCRUDTopics(Long topicid, Map<String, Claim> userClaim) {
        Topic topic = topicRepo.findById(topicid).get();

        if (topic.getUser().get_id().equals(userClaim.get("_id").asLong())) {
            return true;
        }

        String role = userClaim.get("role").asString();
        JSONObject permissions = userClaim.get("permissions").as(JSONObject.class);
        if (permissions.get("categories").equals("") || permissions.get("categories") == null || permissions.size() == 0 || role.equals("user")) return false;
        Map<String, Object> categoriesMap = (Map<String, Object>) permissions.get("categories");
        boolean permissionInCurrentCategory = categoriesMap.containsKey(topic.getCategory().getSlug());
        return permissionInCurrentCategory && role.equals("admin") || role.equals("moderator");
    }
}
