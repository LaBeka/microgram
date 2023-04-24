package com.example.microgram.dto.post;

import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PostDtoShow {
    private String user;
    private String photo;
    private LocalDateTime postDateTime;
    private String description;

    public static PostDtoShow buildPostDTO(Post post, User user){
        return PostDtoShow
                .builder()
                .user(user.getAccountName())
                .photo(post.getPhoto())
                .description(post.getDescription())
                .postDateTime(post.getPostDateTime())
                .build();
    }

}
