package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.dto.converter.UserDTOConverter;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.services.CategoryService;
import com.liceu.sromerom.discussionforum.services.TokenService;
import com.liceu.sromerom.discussionforum.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> register(@RequestBody User newUser) {
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
        System.out.println(userToLogin);
        if (!userService.validateUser(userToLogin)) {
            JSONObject errorJSON = new JSONObject();
            errorJSON.put("message", "Incorrect email or password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON.toJSONString());
        }

        String token = tokenService.generateNewToken(userToLogin);
        System.out.println("Token: "+  token);

        JSONObject json = new JSONObject();
        JSONObject permissions = new JSONObject();

        UserDTO userDTO = userDTOConverter.convertToDto(userService.findUserByEmail(userToLogin.getEmail()));
        //userDTO.set_id(userService.findUserByEmail(userToLogin.getEmail()).getUserid());
        //userDTO.setId(userService.findUserByEmail(userToLogin.getEmail()).getUserid());
        userDTO.set__v("0");


        String [] root = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
        List<Category> categories = categoryService.findAll();

        permissions.put("categories", categories);
        permissions.put("root", root);

        userDTO.setPermissions(permissions);
        json.put("token", token);
        json.put("user", userDTO);

        System.out.println("Login: " + json.toJSONString());
        return ResponseEntity.ok(json);
        //return new ResponseEntity<>(json.toJSONString(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getprofile")
    public ResponseEntity<?> getProfile(@RequestAttribute String user) {
        System.out.println("Substract del token!!!: " + user);
        if (user != null && user != "") {
            User getInfoProfile = userService.findUserByEmail(user);
            UserDTO userDTO = userDTOConverter.convertToDto(getInfoProfile);

            String [] root = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
            List<Category> categories = categoryService.findAll();

            JSONObject permissions = new JSONObject();
            permissions.put("categories", categories);
            permissions.put("root", root);
            userDTO.set__v("0");
            userDTO.setPermissions(permissions);
            return ResponseEntity.ok(userDTO);

        }

        return ResponseEntity.notFound().build();
    }


}
