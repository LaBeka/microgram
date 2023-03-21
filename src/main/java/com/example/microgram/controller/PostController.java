package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.Post;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private UserService userService;

    @PostMapping(value = "/post")
    public ResponseEntity createNewPost(PostDto data) {
        UserDto findUser = userService.getUserById(data.getUserId());

        if(findUser == null){
            return new ResponseEntity(postService.addNewPost(data), HttpStatus.OK);
        } else {
            return new ResponseEntity("Not found user!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/post/{userId}")
    public ResponseEntity<List<PostDto>> getOthersPosts(@PathVariable int userId){
        return new ResponseEntity<List<PostDto>>(
                postService.getOthersPosts(userId),
                HttpStatus.OK
        );
    }
    @GetMapping(value = "/followingsPost/{userId}")
    public ResponseEntity<List<PostDto>> getPostsBySubscription(@PathVariable int userId){
        return new ResponseEntity<List<PostDto>>(
                postService.getOthersPostsByFollowings(userId),
                HttpStatus.OK
        );
    }
    @GetMapping(value = "/post/{postId}")
    public ResponseEntity checkLikeOnPost(@PathVariable int postId){
        Optional ifTrue = postService.checkLikeOnPost(postId);

        if(ifTrue.isPresent()){
            return new ResponseEntity(ifTrue, HttpStatus.OK);
        } else {
            return new ResponseEntity("Not like on post!", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/likeThePost")
    public ResponseEntity likeThePost(@RequestParam MultiValueMap<String, String> formData){
        UserDto findUser = userService.getUserById(Integer.parseInt(String.valueOf(formData.get("userId"))));

        if(findUser == null){
            return new ResponseEntity(postService.putLikeOnThePost(formData), HttpStatus.OK);
        } else {
            return new ResponseEntity("Not found user", HttpStatus.BAD_REQUEST);
        }
    }
}
