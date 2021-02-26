package com.liceu.sromerom.discussionforum.services;


import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.UserDTORequest;
import com.liceu.sromerom.discussionforum.entities.User;

public interface UserService {
    boolean existsUserByEmail(String email);

    boolean validateUser(UserDTORequest user);

    boolean createUser(UserDTORequest user);

    User findUserByEmail(String email);

    User editProfile(String email, EditProfileUserDTORequest editProfileUserDTORequest);

    boolean editPasswordProfile(String email, EditPasswordUserDTORequest editPasswordUserDTORequest);
}
