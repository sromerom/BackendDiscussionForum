package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.converter.ReplyDTOConverter;
import com.liceu.sromerom.discussionforum.entities.Reply;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.services.ReplyService;
import com.liceu.sromerom.discussionforum.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepliesController {

    @Autowired
    ReplyService replyService;

    @Autowired
    UserService userService;

    @Autowired
    ReplyDTOConverter replyDTOConverter;

    @PostMapping("/topics/{topicId}/replies")
    public ResponseEntity<?> postReplies(@RequestBody ReplyDTO replyDTO, @PathVariable Long topicId, @RequestAttribute String user) {

        if (user != null || user == "") {
            Reply replyCreated = replyService.createReply(user, topicId, replyDTO);
            String message;
            JSONObject json = new JSONObject();
            if (replyCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(replyDTOConverter.convertToDto(replyCreated));
            } else {
                json.put("message", "error");
                message = json.toJSONString();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/topics/{topicId}/replies/{replyId}")
    public ResponseEntity<?> putReplies(@RequestBody ReplyDTO modifyReplyDTO, @PathVariable Long topicId, @PathVariable Long replyId) {
        if (replyService.existsReply(replyId)) {
            Reply editedReply = replyService.editReply(topicId, replyId, modifyReplyDTO);
            return ResponseEntity.ok(replyDTOConverter.convertToDto(editedReply));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    ///topics/:topicId/replies/:replyId
    @DeleteMapping("/topics/{topicId}/replies/{replyId}'")
    public ResponseEntity<?> deleteCategories(@PathVariable Long topicId, @PathVariable Long replyId) {
        if (replyService.existsReply(replyId)) {
            return ResponseEntity.ok(replyService.deleteReply(replyId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
