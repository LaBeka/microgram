package com.example.microgram.dto;

import com.example.microgram.dao.UserDao;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
public class PostDtoShow {
    private String user;
    private String photo;
    private LocalDate postDate;
    private String description;

    public static PostDtoShow buildPostDTO(Post post, User user){
        return PostDtoShow
                .builder()
                .user(user.getAccountName())
                .photo(post.getPhoto())
                .description(post.getDescription())
                .postDate(post.getPostDate())
                .build();
    }

}
