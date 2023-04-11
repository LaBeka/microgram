package com.example.microgram.controller;

import com.example.microgram.dto.FollowUserDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService service;

    @PostMapping("/follow/{userBeingFollowed}")
    public boolean followUser(@PathVariable Long userBeingFollowed, Authentication auth){
        User follower = (User) auth.getPrincipal();
        ResultDto result = service.follow(userBeingFollowed, follower);
        System.out.println(result.message.toUpperCase());
        return result.isTrue;
    }

    @PostMapping("/unFollow/{userBeingFollowed}")
    public Boolean unFollowUser(@PathVariable Long userBeingFollowed, Authentication auth){
        User follower = (User) auth.getPrincipal();
        ResultDto result = service.unFollow(userBeingFollowed, follower);
        System.out.println(result.message);
        return result.isTrue;
    }

    @GetMapping(value = "/myFollowings")
    public ResponseEntity getMyFollowings(Authentication auth){
        User user = (User) auth.getPrincipal();
        Optional<List<FollowUserDto>> listFollowings = service.getMyFollowings(user);

        if(listFollowings.get().size() == 0){
            return new ResponseEntity("No user you follow - to show!", HttpStatus.OK);
        }
        return new ResponseEntity(listFollowings.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/myFollowers")
    public ResponseEntity getMyFollowers(Authentication auth){
        User user = (User) auth.getPrincipal();
        Optional<List<FollowUserDto>> listFollowers = service.getMyFollowers(user);

        if(listFollowers.get().size() == 0){
            return new ResponseEntity("No users  follow you !", HttpStatus.OK);
        }
        return new ResponseEntity(listFollowers.get(), HttpStatus.OK);
    }
}
