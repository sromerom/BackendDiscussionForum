package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicCreateDTO;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicCreateDTOconverter {
    @Autowired
    ModelMapper modelMapper;

    public Topic convertToTopic(TopicCreateDTO topicCreateDTO) {
        modelMapper.typeMap(TopicCreateDTO.class, Topic.class).addMappings(mapper -> mapper.map(src -> src.getCategory(), (dest, v) -> dest.getCategory().getSlug()));
        modelMapper.addConverter(toEmpty);
        return modelMapper.map(topicCreateDTO, Topic.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
}
