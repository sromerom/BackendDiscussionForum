package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.UserDTO;

import java.util.Map;

public interface TokenService {
    String generateNewToken(UserDTO user);
    Map<String, Claim> getSubject(String token);
}
