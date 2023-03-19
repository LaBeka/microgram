package com.example.microgram.dto;

import com.example.microgram.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FollowDto {
    private User userBeingFollowed;
    private User userFollowing;
    private LocalDate followDate;
    private Long countFollowers;
}
