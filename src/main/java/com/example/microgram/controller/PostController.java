package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.PostDtoShow;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/post")
public class PostController {
    private PostService postService;

    @PostMapping(value = "/create")
    public ResultDto createNewPost(@RequestBody PostDto data, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return postService.createNewPost(data, user);
    }

    @GetMapping(value = "/myPosts")
    public ResponseEntity getMyPosts(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> myPosts = postService.getMyPosts(user);
        if(myPosts.size() == 0){
            return new ResponseEntity("No my posts to show", HttpStatus.OK);
        }
        return new ResponseEntity(myPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/othersPosts")
    public ResponseEntity getOthersPosts(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> othersPosts = postService.getOthersPosts(user);
        if(othersPosts.size() == 0){
            return new ResponseEntity("No Posts found", HttpStatus.OK);
        }
        return new ResponseEntity(othersPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/followingsPosts")
    public ResponseEntity<List<PostDto>> getPostsBySubscription(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> posts = postService.getOthersPostsByFollowings(user);
        if(posts.size() == 0){
            return new ResponseEntity("no posts out there.", HttpStatus.OK
            );
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }
    @DeleteMapping(value = "/deletePost/{postId}")
    public ResultDto deleteThePost(Authentication auth, @PathVariable Long postId){
        User user = (User) auth.getPrincipal();
        return postService.deleteThePost(postId, user);
    }
}
