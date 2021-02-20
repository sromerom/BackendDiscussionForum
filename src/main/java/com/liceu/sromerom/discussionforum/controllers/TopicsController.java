package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.dto.TopicByIdDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.dto.converter.ReplyDTOConverter;
import com.liceu.sromerom.discussionforum.dto.converter.TopicByIdDTOConverter;
import com.liceu.sromerom.discussionforum.dto.converter.TopicDTOConverter;
import com.liceu.sromerom.discussionforum.dto.converter.UserDTOConverter;
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

    @GetMapping("/topics/{id}")
    public ResponseEntity<?> getTopic(@PathVariable Long id) {
        Topic topic = topicService.findTopicById(id);
        TopicByIdDTO topicByIdDTO = topicByIdDTOConverter.convertToDto(topic);
        topicByIdDTO.getUser().setAvatarUrl("");

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
                return ResponseEntity.status(HttpStatus.CREATED).body(topicDTOConverter.convertToDto(topicCreated));
            } else {
                json.put("message", "error");
                message = json.toJSONString();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<?> putTopic(@RequestBody String payload, @PathVariable Long id) {
        if (topicService.existsTopic(id)) {
            Topic edited = topicService.editTopic(id, payload);
            return ResponseEntity.ok(topicDTOConverter.convertToDto(edited));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long id) {
        if (topicService.existsTopic(id)) {
            return ResponseEntity.ok(topicService.deleteTopic(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
