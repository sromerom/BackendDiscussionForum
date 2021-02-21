package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.liceu.sromerom.discussionforum.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret}")
    String tokenSecret;

    @Value("${token.expiration.time}")
    Long tokenExpirationTime;


    @Override
    public String generateNewToken(User user) {
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC256(tokenSecret.getBytes()));
        return token;
    }



    @Override
    public String getSubject(String token) {
        String subject = JWT.require(Algorithm.HMAC256(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        return subject;
    }
}
