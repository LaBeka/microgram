package com.example.microgram.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private int id;
    private int user_id;
    private String photo;
    private String description;
    private Date postDate;
}
