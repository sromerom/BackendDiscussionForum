package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicByIdDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicByIdDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public TopicByIdDTO convertToDto(Topic topic) {
        modelMapper.addConverter(toEmpty);
        modelMapper.typeMap(Topic.class, TopicByIdDTO.class).addMappings(mapper -> mapper.map(src -> src.getReplies(), (dest, v) -> dest.getReplies()));
        return modelMapper.map(topic, TopicByIdDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
}
