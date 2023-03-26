package com.example.microgram.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FollowDto {
    private Long userBeingFollowed; // followers
    private Long userFollowing;//following
}