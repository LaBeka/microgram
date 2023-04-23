package com.example.microgram.dto;

import com.example.microgram.dao.UserDao;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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
