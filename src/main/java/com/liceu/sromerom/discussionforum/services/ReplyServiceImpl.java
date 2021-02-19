package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.repos.ReplyRepo;
import com.liceu.sromerom.discussionforum.repos.TopicRepo;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        //id
        //content
        //createdAt
        //updatedAt
        //replyOwner
        //topic
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
        Reply replyToDelete = replyRepo.findBy_id(replyid);
        replyToDelete.setContent(replyDTO.getContent());
        return replyRepo.save(replyToDelete);
    }

    @Override
    public boolean deleteReply(Long replyid) {
        Reply replyToDelete = replyRepo.findBy_id(replyid);
        if (replyToDelete != null) {
            replyRepo.deleteById(replyid);
            if (replyRepo.findBy_id(replyid) == null) return true;
        }

        return false;
    }

    @Override
    public boolean existsReply(Long replyid) {
        return replyRepo.existsById(replyid);
    }
}
