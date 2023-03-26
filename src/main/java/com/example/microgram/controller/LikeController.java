package com.example.microgram.controller;

import com.example.microgram.dto.LIkeDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {
    private LikeService likeService;

    @PostMapping(value = "/likeThePost")
    public ResultDto likeThePost(@RequestBody LIkeDto data){
        return likeService.putLikeOnThePost(data);
    }

    @GetMapping(value = "/ifLiked/{postId}")
    public ResultDto checkLikeOnThePost(@PathVariable Long postId){
        return likeService.checkLikeOnThePost(postId);
    }

    @PostMapping(value = "/unlikePost/{userId}/{postId}")
    public ResultDto unLikeThePost(@PathVariable Long userId, @PathVariable Long postId){
        return likeService.unLikeOnThePost(userId, postId);
    }
}
