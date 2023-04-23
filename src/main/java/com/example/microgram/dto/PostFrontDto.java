package com.example.microgram.dto;

import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PostFrontDto {
    private String postId;
    private String userName;
    private String photo;
    private String postDateTime;
    private String description;
    public static PostFrontDto buildPost(Post post, User user){
        return PostFrontDto
                .builder()
                .postId(post.getPostId().toString())
                .userName(user.getAccountName())
                .photo(post.getPhoto())
                .description(post.getDescription())
                .postDateTime(post.getPostDateTime().toString())
                .build();
    }
}
