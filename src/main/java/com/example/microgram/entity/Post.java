package com.example.microgram.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Post{
    private int postId;
    private int likeId;
    private int userId;
    private String photo;
    private String description;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date postDate;

}
