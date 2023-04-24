package com.example.microgram.dto.user;

import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String accountName;
    private String email;
    private Long postQuantity;
    private Long followQuantity;
    private Long followerQuantity;
    private String name;

    public static UserDto buildUserDTO(User user, Long postQuantity, Long followQuantity, Long followerQuantity){
        return UserDto.builder()
                .accountName(user.getAccountName())
                .email(user.getEmail())
                .postQuantity(postQuantity)
                .followQuantity(followQuantity)
                .followerQuantity(followerQuantity)
                .name(user.getName())
                .build();
    }

}