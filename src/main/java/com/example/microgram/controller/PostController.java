package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PostController {
    private PostService service;
    private UserService userService;

    @PostMapping(value = "/post")
    public ResponseEntity createNewPost(PostDto data) {
        Optional findUser = userService.getUserById(data.getUser_id());

        if(findUser.isPresent()){
            return new ResponseEntity(service.updateTable(data), HttpStatus.OK);
        } else {
            return new ResponseEntity("Not found user!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/post/{userId}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable int userId){
        return new ResponseEntity<List<Post>>(service.getOthersPosts(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/followingsPost/{userId}")
    public ResponseEntity<List<Post>> getPostsBySubscription(@PathVariable int userId){
        return new ResponseEntity<List<Post>>(service.getOthersPostsByFollowings(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}")
    public ResponseEntity getCheckLikeonPost(@PathVariable int postId){
        Optional ifTrue = service.checkLikeOnPost(postId);

        if(ifTrue.isPresent()){
            return new ResponseEntity(ifTrue, HttpStatus.OK);
        } else {
            return new ResponseEntity("Not like on post!", HttpStatus.OK);
        }
    }
}
