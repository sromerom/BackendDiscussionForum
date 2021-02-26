package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.ReplyDTO;
import com.liceu.sromerom.discussionforum.entities.Reply;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReplyDTOConverter {
    @Autowired
    ModelMapper modelMapper;

    public ReplyDTO convertToDto(Reply reply) {
        modelMapper.typeMap(Reply.class, ReplyDTO.class).addMappings(mapper -> mapper.map(src -> src.getTopic().get_id(), (dest, v) -> dest.getTopic()));
        modelMapper.addConverter(toEmpty);
        return modelMapper.map(reply, ReplyDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
}
