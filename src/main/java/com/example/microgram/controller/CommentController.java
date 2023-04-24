package com.example.microgram.controller;

import com.example.microgram.dto.*;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import com.example.microgram.service.CommentService;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/comment")
public class CommentController {
    private CommentService service;
    private UserService userService;
    private PostService postService;

//    @PostMapping("/create")
//    public ResultDto createNewComment(@RequestBody CommentDto data, Authentication auth) {
//        User user = (User) auth.getPrincipal();
//        return service.addNewComment(data, user);
//    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity createNewComment(@RequestParam("commentText") String commentText,
                                           @RequestParam("userId") String userId,
                                           @RequestParam("postId") String postId) throws IOException {
        Long userID = 0L;
        Long postID = 0L;
        try{
            userID = Long.parseLong(userId);
            postID = Long.parseLong(postId);
        } catch (Exception e){
            return new ResponseEntity("Id is not a digit", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.findUserById(userID);
        if(user.isEmpty()) {
            return new ResponseEntity("No user found by this id", HttpStatus.BAD_REQUEST);
        }
        Optional<Post> post = postService.findPostById(postID);
        if(post.isEmpty()) {
            return new ResponseEntity("No post found by this id", HttpStatus.BAD_REQUEST);
        }
        CommentDto comm = new CommentDto(commentText, post.get().getPostId());
        String error = comm.validateCommentData();
        if(!error.isEmpty()){
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
        CommentFrontDto commentFrontDto = service.addNewComment(comm, user.get());
        System.out.println(commentFrontDto.toString());//when comment is created it will always appear in  the browser
        return new ResponseEntity(commentFrontDto, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity getAllComments(){
        List<CommentFrontDto> allComm = service.getAll();
        if(allComm.size() == 0){
            return new ResponseEntity("No Posts found", HttpStatus.OK);
        }
        System.out.println(allComm.toString());
        return new ResponseEntity(allComm, HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResultDto deleteComment(@RequestBody CommentDto data, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return service.unComment(data, user);
    }

    @PostMapping("/updateText/{commentId}")
    public ResultDto updateCommentText(@RequestBody String text, @PathVariable Long commentId, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return service.updateCommentText(commentId, user, text);
    }
}
