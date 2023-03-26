package com.example.microgram.controller;

import com.example.microgram.dto.FollowDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService service;

    @PostMapping
    public ResultDto followUser(@RequestBody FollowDto data){
        return service.follow(data);
    }
    @PostMapping("/unfollow")
    public ResultDto unfollowUser(@RequestBody FollowDto data){
        return service.unfollow(data);
    }


}
