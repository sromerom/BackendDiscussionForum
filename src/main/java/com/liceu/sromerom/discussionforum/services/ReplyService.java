package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Map;

public interface ReplyService {
    Reply createReply(String email, Long topicid, ReplyDTO replyDTO);

    Reply editReply(Long topicid, Long replyid, ReplyDTO replyDTO);

    boolean deleteReply(Long replyid);

    boolean existsReply(Long replyid);

    boolean canCRUDReply(Long topicid, Long replyid, @RequestAttribute Map<String, Claim> user);
}
