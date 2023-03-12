package com.example.microgram.entity;

import lombok.Data;

@Data
public class Follow {
    private User userBeingFollowed;
    private User userFollowing;
    private String followDate;
}
