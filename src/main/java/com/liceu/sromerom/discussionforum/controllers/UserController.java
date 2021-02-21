package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTO;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.dto.UserRegisterDTO;
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
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO newUser) {
        System.out.println(newUser);
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
    public ResponseEntity<?> login(@RequestBody User userToLogin) {
        if (!userService.validateUser(userToLogin)) {
            JSONObject errorJSON = new JSONObject();
            errorJSON.put("message", "Incorrect email or password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON.toJSONString());
        }

        String token = tokenService.generateNewToken(userToLogin);

        UserDTO userDTO = userDTOConverter.convertToDto(userService.findUserByEmail(userToLogin.getEmail()));
        userDTO.completePermissions(categoryService.findAll());

        JSONObject jsonLogin = new JSONObject();

        jsonLogin.put("user", userDTO);
        jsonLogin.put("token", token);

        return ResponseEntity.ok(jsonLogin);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestAttribute String user, @RequestBody EditProfileUserDTO editProfileUserDTO) {
        if (user != null && user != "") {
            if (userService.existsUserByEmail(user)) {
                UserDTO userDTO = userDTOConverter.convertToDto(userService.editProfile(user, editProfileUserDTO));
                userDTO.completePermissions(categoryService.findAll());
                String token = tokenService.generateNewToken(userService.findUserByEmail(userDTO.getEmail()));
                JSONObject jsonLogin = new JSONObject();
                jsonLogin.put("user", userDTO);
                jsonLogin.put("token", token);

                return ResponseEntity.ok(jsonLogin);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile/password")
    public ResponseEntity<?> editPassword(@RequestAttribute String user, @RequestBody EditPasswordUserDTO editPasswordUserDTO) {
        if (user != null && user != "") {
            if (userService.existsUserByEmail(user)) {
                boolean passwordChanged = userService.editPasswordProfile(user, editPasswordUserDTO);
                if (passwordChanged) return ResponseEntity.ok(true);
            }

            JSONObject json = new JSONObject();
            json.put("message", "Your current password is wrong");
            String message = json.toJSONString();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getprofile")
    public ResponseEntity<?> getProfile(@RequestAttribute String user) {
        if (user != null && user != "") {
            User getInfoProfile = userService.findUserByEmail(user);
            UserDTO userDTO = userDTOConverter.convertToDto(getInfoProfile);
            userDTO.completePermissions(categoryService.findAll());
            return ResponseEntity.ok(userDTO);

        }

        return ResponseEntity.notFound().build();
    }


}
