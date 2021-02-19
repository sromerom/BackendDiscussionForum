package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;

public interface ReplyService {
    Reply createReply(String email, Long topicid, ReplyDTO replyDTO);
    Reply editReply(Long topicid, Long replyid, ReplyDTO replyDTO);

    boolean deleteReply(Long replyid);
    boolean existsReply(Long replyid);
}
