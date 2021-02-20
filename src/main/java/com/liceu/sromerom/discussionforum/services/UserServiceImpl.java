package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTO;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTO;
import com.liceu.sromerom.discussionforum.dto.UserDTO;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import com.liceu.sromerom.discussionforum.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean validateUser(User userToValidate) {
        try {
            User existsUserWithEmail = userRepo.findByEmail(userToValidate.getEmail());
            if (userRepo.existsByEmail(userToValidate.getEmail()) && existsUserWithEmail != null && userToValidate.getPassword() != null) {
                String storedPassword = existsUserWithEmail.getPassword();
                return HashUtil.validatePassword(userToValidate.getPassword(), storedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean createUser(User user) {
        try {
            if(!userRepo.existsByEmail(user.getEmail())) {
                String generatedSecuredPassword = HashUtil.generatePasswordHash(user.getPassword());
                user.setPassword(generatedSecuredPassword);
                User insertedUser = userRepo.save(user);
                if (insertedUser != null) return true;
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
           return false;
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User editProfile(String email, EditProfileUserDTO editProfileUserDTO) {
        User userToEdit = userRepo.findByEmail(email);
        userToEdit.setEmail(editProfileUserDTO.getEmail());
        userToEdit.setName(editProfileUserDTO.getName());

        if (editProfileUserDTO.getAvatar() != null) {
            userToEdit.setAvatarUrl(editProfileUserDTO.getAvatar());
        }

        return userRepo.save(userToEdit);
    }

    @Override
    public boolean editPasswordProfile(String email, EditPasswordUserDTO editPasswordUserDTO) {
        try {
            User userToCheck = new User();
            userToCheck.setPassword(editPasswordUserDTO.getCurrentPassword());
            userToCheck.setEmail(email);

            if (validateUser(userToCheck)) {
                User userToEditPassword = userRepo.findByEmail(email);
                String generatedSecuredPassword = HashUtil.generatePasswordHash(editPasswordUserDTO.getNewPassword());
                userToEditPassword.setPassword(generatedSecuredPassword);
                User insertedUser = userRepo.save(userToEditPassword);
                if (insertedUser != null) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
