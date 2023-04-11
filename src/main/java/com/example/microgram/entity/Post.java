package com.example.microgram.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Post implements Likable{
    private Long postId;
    private Long userId;
    private String photo;
    private String description;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate postDate;

//    @Override
//    public LikeType getType() {
//        return LikeType.POST;
//    }

    @Override
    public Long getId() {
        return postId;
    }
}
