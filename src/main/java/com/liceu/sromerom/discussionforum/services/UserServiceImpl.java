package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import com.liceu.sromerom.discussionforum.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

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
}
