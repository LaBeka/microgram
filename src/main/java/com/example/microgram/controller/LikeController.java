package com.example.microgram.controller;

import com.example.microgram.dto.LIkeDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {
    private LikeService likeService;
    @PostMapping(value = "/likeThePost")
    public ResultDto likeThePost(@RequestBody LIkeDto data, Authentication auth){
        User user = (User) auth.getPrincipal();
        return likeService.likeThePost(data, user);
    }

    @GetMapping(value = "/likePostQty/{postId}")
    public ResultDto getLikesOfThePost(@PathVariable Long postId){
        return likeService.qtyOfLikesOfPost(postId);
    }

    @GetMapping(value = "/ILikedPost/{postId}")
    public boolean checkMyLikeOnThePost(
            @PathVariable Long postId,
            Authentication auth){
        User user = (User)auth.getPrincipal();
        ResultDto result = likeService.ifILikedThePost(postId, user);
        return result.isTrue;
    }
    @PostMapping(value = "/unlikePost/{postId}")
    public ResultDto unLikeThePost(
            @PathVariable Long postId,
            Authentication auth){
        User user = (User)auth.getPrincipal();
        return likeService.unLikeOnThePost(user, postId);
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping(value = "/likeTheComment")
    public ResultDto likeTheComment(@RequestBody LIkeDto data, Authentication auth){
        User user = (User) auth.getPrincipal();
        return likeService.likeTheComment(data, user);
    }

    @GetMapping(value = "/likeCommQty/{commentId}")
    public ResultDto getLikesOfTheComment(@PathVariable Long commentId){
        return likeService.qtyOfLikesOfComment(commentId);
    }

    @GetMapping(value = "/ILikedComm/{commentId}")
    public boolean checkMyLikeOnTheComm(
            @PathVariable Long commentId,
            Authentication auth){
        User user = (User)auth.getPrincipal();
        ResultDto result = likeService.ifILikedTheComment(commentId, user);
        return result.isTrue;
    }

    @PostMapping(value = "/unlikeComment/{CommentId}")
    public ResultDto unLikeTheComment(
            @PathVariable Long CommentId,
            Authentication auth){
        User user = (User)auth.getPrincipal();
        return likeService.unLikeOnTheComment(user, CommentId);
    }
}
