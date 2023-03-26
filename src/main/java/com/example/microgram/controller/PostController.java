package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/post")
public class PostController {
    private PostService postService;

    @PostMapping(value = "/create")
    public ResultDto createNewPost(@RequestBody PostDto data) {
        return postService.createNewPost(data);
    }

    @GetMapping(value = "/getListPosts/{userId}")
    public ResponseEntity getOthersPosts(@PathVariable Long userId){
        List<PostDto> othersPosts = postService.getOthersPosts(userId);
        if(othersPosts == null){
            return new ResponseEntity("No User found", HttpStatus.OK);
        }
        return new ResponseEntity(othersPosts, HttpStatus.OK);
    }
    @GetMapping(value = "/followingsPost/{userId}")
    public ResponseEntity<List<PostDto>> getPostsBySubscription(@PathVariable Long userId){
        List<PostDto> posts = postService.getOthersPostsByFollowings(userId);
        if(posts.size() == 0){
            return new ResponseEntity("no posts out there.", HttpStatus.OK
            );
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }
    @DeleteMapping(value = "/deletePost")
    public ResultDto deleteThePost(@RequestBody PostDto data){
        return postService.deleteThePost(data);
    }
}
