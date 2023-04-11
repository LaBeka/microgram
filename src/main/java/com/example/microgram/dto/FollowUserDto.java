package com.example.microgram.dto;

import com.example.microgram.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserDto {
    private String accountName;
    private String name;

    public static FollowUserDto buildUserFollow(User user){
        return FollowUserDto.builder()
                .accountName(user.getAccountName())
                .name(user.getName())
                .build();
    }
}
