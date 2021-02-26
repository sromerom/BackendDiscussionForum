package com.liceu.sromerom.discussionforum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTORequest;
import com.liceu.sromerom.discussionforum.dto.converter.UserDTOConverter;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.services.CategoryService;
import com.liceu.sromerom.discussionforum.services.TokenService;
import com.liceu.sromerom.discussionforum.services.UserService;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserDTOConverter userDTOConverter;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTORequest newUser) {
        boolean userCreated = userService.createUser(newUser);
        String message;
        JSONObject json = new JSONObject();
        if (userCreated) {
            json.put("message", "done");
            message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            json.put("message", "error");
            message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTORequest userToLogin) {
        if (!userService.validateUser(userToLogin)) {
            JSONObject errorJSON = new JSONObject();
            errorJSON.put("message", "Incorrect email or password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON.toJSONString());
        }

        UserDTO userDTO = userDTOConverter.convertToDto(userService.findUserByEmail(userToLogin.getEmail()));
        //Completam el permissos segons el role d'usuari registrat
        userDTO.completePermissions(categoryService.findAll());

        String token = tokenService.generateNewToken(userDTO);
        JSONObject jsonLogin = new JSONObject();

        jsonLogin.put("user", userDTO);
        jsonLogin.put("token", token);

        return ResponseEntity.ok(jsonLogin);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestBody EditProfileUserDTORequest editProfileUserDTORequest, @RequestAttribute Map<String, Claim> user) {
        String userEmail = user.get("email").asString();
        if (userService.existsUserByEmail(userEmail)) {
            UserDTO userDTO = userDTOConverter.convertToDto(userService.editProfile(userEmail, editProfileUserDTORequest));
            userDTO.completePermissions(categoryService.findAll());
            String token = tokenService.generateNewToken(userDTO);
            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("user", userDTO);
            jsonLogin.put("token", token);

            return ResponseEntity.ok(jsonLogin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile/password")
    public ResponseEntity<?> editPassword(@RequestBody EditPasswordUserDTORequest editPasswordUserDTORequest, @RequestAttribute Map<String, Claim> user) {
        String userEmail = user.get("email").asString();
        if (userService.existsUserByEmail(userEmail)) {
            boolean passwordChanged = userService.editPasswordProfile(userEmail, editPasswordUserDTORequest);
            if (passwordChanged) return ResponseEntity.ok(true);
        }

        JSONObject json = new JSONObject();
        json.put("message", "Your current password is wrong");
        String message = json.toJSONString();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @GetMapping("/getprofile")
    public ResponseEntity<?> getProfile(@RequestAttribute Map<String, Claim> user) {
        String userEmail = user.get("email").asString();
        User getInfoProfile = userService.findUserByEmail(userEmail);
        UserDTO userDTO = userDTOConverter.convertToDto(getInfoProfile);
        //Completam el permissos segons el role d'usuari
        userDTO.completePermissions(categoryService.findAll());
        return ResponseEntity.ok(userDTO);
    }


}
