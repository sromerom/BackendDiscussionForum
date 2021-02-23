package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.dto.TopicByIdDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.converter.*;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.services.TopicService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    TopicCreateResponseConverter topicCreateResponseConverter;

    @Autowired
    UserDTOConverter userDTOConverter;

    @Autowired
    ReplyDTOConverter replyDTOConverter;

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<?> getTopics(@PathVariable String slug) {
        List<Topic> result = topicService.findByCategory(slug);
        List<TopicDTO> dtoList = result.stream()
                .map(topic -> {
                    TopicDTO topicDTO = topicDTOConverter.convertToDto(topic);
                    return topicDTO;
                })
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
    public ResponseEntity<?> postTopic(@RequestAttribute String user, @RequestBody String payload) {
        System.out.println(payload);

        if (user != null && user != "") {
            Topic topicCreated = topicService.createTopic(user, payload);
            String message;
            JSONObject json = new JSONObject();
            if (topicCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(topicCreateResponseConverter.convertToDto(topicCreated));
            } else {
                json.put("message", "error");
                message = json.toJSONString();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/topics/{topicId}")
    public ResponseEntity<?> putTopic(@RequestBody String payload, @PathVariable Long topicId) {
        if (topicService.existsTopic(topicId)) {
            Topic edited = topicService.editTopic(topicId, payload);
            return ResponseEntity.ok(topicByIdDTOConverter.convertToDto(edited));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long topicId) {
        if (topicService.existsTopic(topicId)) {
            return ResponseEntity.ok(topicService.deleteTopic(topicId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
