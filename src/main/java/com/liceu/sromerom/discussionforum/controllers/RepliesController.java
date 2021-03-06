package com.liceu.sromerom.discussionforum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.dto.converter.ReplyDTOConverter;
import com.liceu.sromerom.discussionforum.entities.Reply;
import com.liceu.sromerom.discussionforum.services.ReplyService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RepliesController {

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyDTOConverter replyDTOConverter;

    @PostMapping("/topics/{topicId}/replies")
    public ResponseEntity<?> postReplies(@RequestBody ReplyDTO replyDTO, @PathVariable Long topicId, @RequestAttribute Map<String, Claim> user) {
        Reply replyCreated = replyService.createReply(user.get("email").asString(), topicId, replyDTO);
        JSONObject json = new JSONObject();
        if (replyCreated != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(replyDTOConverter.convertToDto(replyCreated));
        } else {
            json.put("message", "error");
            String message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PutMapping("/topics/{topicId}/replies/{replyId}")
    public ResponseEntity<?> putReplies(@RequestBody ReplyDTO modifyReplyDTO, @PathVariable Long topicId, @PathVariable Long replyId, @RequestAttribute Map<String, Claim> user) {
        if(replyService.canCRUDReply(topicId, replyId, user)){
            if (replyService.existsReply(replyId)) {
                Reply editedReply = replyService.editReply(topicId, replyId, modifyReplyDTO);
                return ResponseEntity.ok(replyDTOConverter.convertToDto(editedReply));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/topics/{topicId}/replies/{replyId}")
    public ResponseEntity<?> deleteReplies(@PathVariable Long topicId, @PathVariable Long replyId, @RequestAttribute Map<String, Claim> user) {
        if(replyService.canCRUDReply(topicId, replyId, user)){
            if (replyService.existsReply(replyId)) {
                return ResponseEntity.ok(replyService.deleteReply(replyId));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
