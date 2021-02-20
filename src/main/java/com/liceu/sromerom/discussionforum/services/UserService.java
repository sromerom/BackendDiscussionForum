package com.liceu.sromerom.discussionforum.services;


import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTO;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.entities.User;

public interface UserService {

    boolean existsUserByEmail(String email);
    boolean validateUser(User user);
    boolean createUser(User user);
    User findUserByEmail(String email);
    User editProfile(String email, EditProfileUserDTO editProfileUserDTO);

    boolean editPasswordProfile(String email, EditPasswordUserDTO editPasswordUserDTO);
}
