package com.liceu.sromerom.discussionforum.exceptions;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException() {
        JSONObject json = new JSONObject();
        json.put("Message", "Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
    }
}
