package com.example.microgram.controller;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/comment")
public class CommentController {
    private CommentService service;

    @PostMapping("/create")
    public ResultDto createNewComment(@RequestBody CommentDto data, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return service.addNewComment(data, user);
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
