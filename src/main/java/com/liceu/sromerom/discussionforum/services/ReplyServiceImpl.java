package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.repos.ReplyRepo;
import com.liceu.sromerom.discussionforum.repos.TopicRepo;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    ReplyRepo replyRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TopicRepo topicRepo;

    @Override
    public Reply createReply(String email, Long topicid, ReplyDTO replyDTO) {
        User userToAdd = userRepo.findByEmail(email);
        Reply replyToCreate = new Reply();
        replyToCreate.setContent(replyDTO.getContent());
        replyToCreate.setCreatedAt(LocalDateTime.now());
        replyToCreate.setUpdatedAt(LocalDateTime.now());
        replyToCreate.setUser(userToAdd);
        replyToCreate.setTopic(topicRepo.findById(topicid).get());
        return replyRepo.save(replyToCreate);
    }

    @Override
    public Reply editReply(Long topicid, Long replyid, ReplyDTO replyDTO) {
        Reply replyToDelete = replyRepo.findById(replyid).get();
        replyToDelete.setContent(replyDTO.getContent());
        return replyRepo.save(replyToDelete);
    }

    @Override
    public boolean deleteReply(Long replyid) {
        Optional<Reply> replyToDelete = replyRepo.findById(replyid);
        if (replyToDelete.isPresent()) {
            replyRepo.deleteById(replyid);
            Optional<Reply> exists = replyRepo.findById(replyid);;
            return exists.isEmpty();
        }
        return false;
    }

    @Override
    public boolean existsReply(Long replyid) {
        return replyRepo.existsById(replyid);
    }

    @Override
    public boolean canCRUDReply(Long topicid, Long replyid, Map<String, Claim> userClaim) {
        Reply reply = replyRepo.findById(replyid).get();
        Topic topic = topicRepo.findById(reply.getTopic().get_id()).get();

        if (reply.getUser().get_id().equals(userClaim.get("_id").asLong())) {
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
