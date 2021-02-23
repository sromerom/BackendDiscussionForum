package com.liceu.sromerom.discussionforum.services;

import com.liceu.sromerom.discussionforum.entities.Image;
import com.liceu.sromerom.discussionforum.repos.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepo imageRepo;

    @Override
    public byte[] findDataImageByName(String name) {
        Image image = imageRepo.findByName(name);
        return image.getPhoto();
    }
}
