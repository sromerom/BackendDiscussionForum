package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.dto.EditPasswordUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.EditProfileUserDTORequest;
import com.liceu.sromerom.discussionforum.dto.UserDTORequest;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.entities.Image;
import com.liceu.sromerom.discussionforum.entities.User;
import com.liceu.sromerom.discussionforum.repos.CategoryRepo;
import com.liceu.sromerom.discussionforum.repos.ImageRepo;
import com.liceu.sromerom.discussionforum.repos.UserRepo;
import com.liceu.sromerom.discussionforum.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ImageRepo imageRepo;

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean validateUser(UserDTORequest userToValidate) {
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
    public boolean createUser(UserDTORequest user) {
        User userToCreate = new User();
        try {
            if(!userRepo.existsByEmail(user.getEmail())) {
                String generatedSecuredPassword = HashUtil.generatePasswordHash(user.getPassword());
                userToCreate.setName(user.getName());
                userToCreate.setRole(user.getRole());
                userToCreate.setEmail(user.getEmail());
                userToCreate.setAvatar(null);
                userToCreate.setPassword(generatedSecuredPassword);

                if (user.getModerateCategory() != null && !user.getModerateCategory().equals("")) {
                    Category categoryToModerate = categoryRepo.findBySlug(user.getModerateCategory());

                    if (userToCreate.getModeratedCategories() == null) {
                        Set<Category> categoryToAdd = new HashSet<>();
                        categoryToAdd.add(categoryToModerate);
                        userToCreate.setModeratedCategories(categoryToAdd);
                    } else {
                        userToCreate.getModeratedCategories().add(categoryToModerate);
                    }
                }
                User insertedUser = userRepo.save(userToCreate);
                return true;
            }
        } catch (Exception e) {
           return false;
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User editProfile(String email, EditProfileUserDTORequest editProfileUserDTORequest) {
        User userToEdit = userRepo.findByEmail(email);
        userToEdit.setEmail(editProfileUserDTORequest.getEmail());
        userToEdit.setName(editProfileUserDTORequest.getName());
        Charset charset = StandardCharsets.US_ASCII;

        if (editProfileUserDTORequest.getAvatar() != null) {

            //Si ja te carregada una foto, primer s'ha d'eliminar de la base de dades
            if (userToEdit.getAvatar() != null) {
                Image imageToDelete = userToEdit.getAvatar();
                imageRepo.delete(imageToDelete);
            }
            Image newImage = new Image();
            newImage.setName(generateAlphanumericString());
            newImage.setPhoto(charset.encode(editProfileUserDTORequest.getAvatar()).array());
            newImage.setUser(userToEdit);
            imageRepo.save(newImage);
            userToEdit.setAvatar(newImage);
        }

        return userRepo.save(userToEdit);
    }

    @Override
    public boolean editPasswordProfile(String email, EditPasswordUserDTORequest editPasswordUserDTORequest) {
        try {

            UserDTORequest userDTORequest = new UserDTORequest();
            userDTORequest.setPassword(editPasswordUserDTORequest.getCurrentPassword());
            userDTORequest.setEmail(email);

            if (validateUser(userDTORequest)) {
                User userToEditPassword = userRepo.findByEmail(email);
                String generatedSecuredPassword = HashUtil.generatePasswordHash(editPasswordUserDTORequest.getNewPassword());
                userToEditPassword.setPassword(generatedSecuredPassword);
                User insertedUser = userRepo.save(userToEditPassword);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private String generateAlphanumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        String generatedString = null;

        while (generatedString == null) {
            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            if (imageRepo.existsByName(generatedString)) generatedString = null;
        }

        return generatedString;
    }
}
