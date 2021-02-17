package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.User;

public interface TokenService {
    String generateNewToken(User user);
    String getSubject(String token);
}
