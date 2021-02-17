package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    @Autowired
    ModelMapper modelMapper;


    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
