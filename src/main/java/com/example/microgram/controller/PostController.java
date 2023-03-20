package com.example.microgram.controller;

import com.example.microgram.entity.Post;
import com.example.microgram.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {
    private PostService service;

    
    @GetMapping(value = "/post/{email}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable String email){
        return new ResponseEntity<List<Post>>(service.getOthersPosts(email), HttpStatus.OK);
    }
}
