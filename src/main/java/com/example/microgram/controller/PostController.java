package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.PostDtoShow;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/post")
public class PostController {
    private PostService postService;
    private UserService userService;

//    @PostMapping(value = "/create")
//    @CrossOrigin(origins = "http://localhost:63342")
//    public ResultDto createNewPost(@RequestParam("photo") MultipartFile photo,
//                                   @RequestParam("description") String description,
//                                   Authentication auth) {
//        User user = (User) auth.getPrincipal();
//        return null; //postService.createNewPost(data, user);
//    }
    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResultDto createNewPost(@RequestParam("photo") MultipartFile photo,
                                   @RequestParam("description") String description,
                                   @RequestParam("userId") String userId) throws IOException {
//        try{Long.tryParseLong(userId)} catch{}
        Optional<User> user = userService.findUserById(Long.parseLong(userId));
        if(user.isEmpty()) {
            return ResultDto.builder()
                    .message("No user found by this id")
                    .build();
        }
        //save file on server
        Path userImageDirectory = Paths.get("data/images/", userId);
        Files.createDirectories(userImageDirectory);
        Path newFilePath = userImageDirectory.resolve(photo.getOriginalFilename()); //ad this file name
        photo.transferTo(newFilePath);
        //create the post for database
        PostDto post = new PostDto(photo.getOriginalFilename(), description);

        return postService.createNewPost(post, user.get());
    }
    //controller for getting the .img from specific post.id from bd
    @GetMapping(value = "{postId}/image")
    public ResponseEntity getImageDataFromPost(@PathVariable String postId){
        return null;
    }
    //and to use it for

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

    ///post/{id}/like
//    public ResultDto likeThePost(Authentication auth, @PathVariable Long postId){
//        User user = (User) auth.getPrincipal();
//        return postService.likeThePost(postId, user);
//    }
    //likethePost(user, postId) ->
    //validate post if exists-> check if i already like this-> if not like it ->
    //likeType = liketype.LIKE pudLikeDataBase(user, postId, likeType)->
    //

    ///post/{id}/comment==addCommentToPost

    //CommentDto
    //post/comment/{id}/like
    //post /getLikeCount/{id}
}
