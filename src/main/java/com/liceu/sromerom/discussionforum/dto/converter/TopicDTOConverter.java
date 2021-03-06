package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TopicDTOConverter {
    @Autowired
    ModelMapper modelMapper;

    public TopicDTO convertToDto(Topic topic) {
        modelMapper.typeMap(Topic.class, TopicDTO.class).addMappings(mapper -> mapper.map(src -> src.getCategory().get_id(), (dest, v) -> dest.getCategory()));
        modelMapper.typeMap(Topic.class, TopicDTO.class).addMappings(mapper -> mapper.map(src -> src.getReplies().size(), (dest, v) -> dest.getNumberOfReplies()));
        modelMapper.addConverter(toEmpty);
        return modelMapper.map(topic, TopicDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
}
