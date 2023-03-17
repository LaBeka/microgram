package com.example.microgram.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Follow {
    private User userBeingFollowed;
    private User userFollowing;
    private LocalDate followDate;
    private Long countFollowers;
}
