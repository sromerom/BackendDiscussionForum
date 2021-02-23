package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.TopicDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.entities.Image;
import com.liceu.sromerom.discussionforum.entities.Topic;
import com.liceu.sromerom.discussionforum.entities.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class UserDTOConverter {
    @Autowired
    ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> mapper.map(src -> src.getAvatar(), (dest, v) -> dest.getAvatarUrl()));
        modelMapper.addConverter(toEmpty);
        modelMapper.addConverter(imageToUrl);
        return modelMapper.map(user, UserDTO.class);
    }


    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };

    Converter<Image, String> imageToUrl = new AbstractConverter<>() {
        @Override
        protected String convert(Image image) {
            if (image == null) return "";
            UriComponents uriComponents = UriComponentsBuilder
                    .newInstance()
                    .scheme("http")
                    .host("localhost:8080")
                    .path("/images/" + image.getName())
                    .build();

            return uriComponents.toString();
        }
    };
}
