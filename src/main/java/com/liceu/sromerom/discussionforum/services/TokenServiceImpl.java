package com.liceu.sromerom.discussionforum.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret}")
    String tokenSecret;

    @Value("${token.expiration.time}")
    Long tokenExpirationTime;


    @Override
    public String generateNewToken(UserDTO user) {
        return JWT.create()
                .withClaim("role", user.getRole())
                .withClaim("_id", user.get_id())
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("avatarUrl", user.getAvatarUrl())
                .withClaim("permissions", user.getPermissions())
                .withClaim("iat", System.currentTimeMillis() / 1000)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC256(tokenSecret.getBytes()));
    }



    @Override
    public Map<String, Claim> getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getClaims();
    }
}
