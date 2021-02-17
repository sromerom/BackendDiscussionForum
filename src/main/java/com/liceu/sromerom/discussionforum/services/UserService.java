package com.liceu.sromerom.discussionforum.services;


import com.liceu.sromerom.discussionforum.entities.User;

public interface UserService {

    boolean validateUser(User user);
    boolean createUser(User user);
    User findUserByEmail(String email);
}
