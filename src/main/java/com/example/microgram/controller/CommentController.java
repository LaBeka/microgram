package com.example.microgram.controller;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/comment")
public class CommentController {
    private CommentService service;

    @PostMapping("/create")
    public ResultDto createNewComment(@RequestBody CommentDto data) {
        return service.addNewComment(data);
    }
    @PostMapping("/uncomment")
    public ResultDto unComment(@RequestBody CommentDto data) {
        return service.unComment(data);
    }
}
