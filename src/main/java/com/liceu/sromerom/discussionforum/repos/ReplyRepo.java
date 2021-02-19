package com.liceu.sromerom.discussionforum.repos;

import com.liceu.sromerom.discussionforum.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    Reply findBy_id(Long replyid);
}
