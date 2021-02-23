package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.dto.TopicByIdDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;
import com.liceu.sromerom.discussionforum.entities.Topic;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopicByIdDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ReplyDTOConverter replyDTOConverter;

    public TopicByIdDTO convertToDto(Topic topic) {
        System.out.println("Mapeando TopicByIdDTO");
        modelMapper.addConverter(toEmpty);
        //modelMapper.typeMap(Topic.class, TopicByIdDTO.class).addMappings(mapper -> mapper.map(src -> src.getReplies(), (dest, v) -> dest.getReplies()));
        modelMapper.addConverter(setToList);
        modelMapper.typeMap(Topic.class, TopicByIdDTO.class).addMappings(mapper -> mapper.map(src -> src.getCategory().getModerators(), (dest, v) -> dest.getUser()));
        return modelMapper.map(topic, TopicByIdDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };

    Converter<Set<Reply>, List<ReplyDTO>> setToList = new AbstractConverter<>() {
        @Override
        protected List<ReplyDTO> convert(Set<Reply> replySet) {
            System.out.println("Entras aqui????");
            List<ReplyDTO> replyDTOList = replySet.stream().map(replyDTOConverter::convertToDto).collect(Collectors.toList());
            System.out.println(replyDTOList);
            return replyDTOList;
        }

    };
}
