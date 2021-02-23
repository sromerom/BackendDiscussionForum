package com.liceu.sromerom.discussionforum.controllers;

import com.liceu.sromerom.discussionforum.services.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImg(@PathVariable String name) {
        byte[] photo = imageService.findDataImageByName(name);
        String photoBase64String = new String(photo).split(",")[1];
        return  Base64.decodeBase64(photoBase64String);
    }
}
