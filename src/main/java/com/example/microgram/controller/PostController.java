package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PostController {
    private PostService service;
    private UserService userService;

    @PostMapping(value = "/post")
    public ResponseEntity putNewPost(PostDto data) {
        Optional findUser = userService.getUserById(data.getUser_id());

        if(findUser.isPresent()){
            return new ResponseEntity(service.updateTable(data), HttpStatus.OK);
        } else {
            return new ResponseEntity("Not found user!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/post/{email}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable String email){
        return new ResponseEntity<List<Post>>(service.getOthersPosts(email), HttpStatus.OK);
    }
}
