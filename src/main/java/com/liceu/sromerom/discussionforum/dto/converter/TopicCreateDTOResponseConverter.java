package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicCreateDTOResponse;
import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.entities.Topic;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicCreateDTOResponseConverter {
    @Autowired
    ModelMapper modelMapper;

    public TopicCreateDTOResponse convertToDto(Topic topic) {
        modelMapper.typeMap(Topic.class, TopicCreateDTOResponse.class).addMappings(mapper -> mapper.map(src -> src.getCategory().get_id(), (dest, v) -> dest.getCategory()));
        modelMapper.typeMap(Topic.class, TopicCreateDTOResponse.class).addMappings(mapper -> mapper.map(src -> src.getUser().get_id(), (dest, v) -> dest.getUser()));
        modelMapper.addConverter(toEmpty);
        return modelMapper.map(topic, TopicCreateDTOResponse.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
}
