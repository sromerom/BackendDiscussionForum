package com.liceu.sromerom.discussionforum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.TopicByIdDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTORequest;
import com.liceu.sromerom.discussionforum.dto.converter.*;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.services.TopicService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TopicsController {

    @Autowired
    TopicService topicService;

    @Autowired
    TopicDTOConverter topicDTOConverter;

    @Autowired
    TopicByIdDTOConverter topicByIdDTOConverter;

    @Autowired
    TopicCreateDTOResponseConverter topicCreateResponseConverter;

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<?> getTopics(@PathVariable String slug) {
        List<Topic> result = topicService.findByCategory(slug);
        List<TopicDTO> dtoList = result.stream()
                .map(topic -> topicDTOConverter.convertToDto(topic))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/topics/{topicid}")
    public ResponseEntity<?> getTopic(@PathVariable Long topicid) {
        topicService.newView(topicid);
        Topic topic = topicService.findTopicById(topicid);
        TopicByIdDTO topicByIdDTO = topicByIdDTOConverter.convertToDto(topic);

        return ResponseEntity.ok(topicByIdDTO);
    }

    @PostMapping("/topics")
    public ResponseEntity<?> postTopic(@RequestBody TopicDTORequest topicDTORequest, @RequestAttribute Map<String, Claim> user) {
        Topic topicCreated = topicService.createTopic(user.get("email").asString(), topicDTORequest);
        if (topicCreated != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(topicCreateResponseConverter.convertToDto(topicCreated));
        } else {
            JSONObject json = new JSONObject();
            json.put("message", "error");
            String message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PutMapping("/topics/{topicId}")
    public ResponseEntity<?> putTopic(@RequestBody TopicDTORequest topicDTORequest, @PathVariable Long topicId, @RequestAttribute Map<String, Claim> user) {
        if (topicService.userCanCRUDTopics(topicId, user)) {
            if (topicService.existsTopic(topicId)) {
                Topic edited = topicService.editTopic(topicId, topicDTORequest);
                return ResponseEntity.ok(topicByIdDTOConverter.convertToDto(edited));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long topicId, @RequestAttribute Map<String, Claim> user) {

        if (topicService.userCanCRUDTopics(topicId, user)) {
            if (topicService.existsTopic(topicId)) {
                return ResponseEntity.ok(topicService.deleteTopic(topicId));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
