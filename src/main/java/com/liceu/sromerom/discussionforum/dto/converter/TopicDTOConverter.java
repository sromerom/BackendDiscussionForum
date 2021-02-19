package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;
import net.minidev.json.JSONObject;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TopicDTOConverter {
    @Autowired
    ModelMapper modelMapper;

    public TopicDTO convertToDto(Topic topic) {
        JSONObject permissions = new JSONObject();
        String[] root = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
        permissions.put("categories", "");
        permissions.put("root", root);

        modelMapper.typeMap(Topic.class, TopicDTO.class).addMappings(mapper -> mapper.map(src -> src.getCategory().get_id(), (dest, v) -> dest.getCategory()));
        modelMapper.addConverter(toEmpty);
        //modelMapper.addMappings(parseCategory);
        return modelMapper.map(topic, TopicDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            System.out.println(source + " es null?");
            return source == null ? "" : source;
        }
    };
}
