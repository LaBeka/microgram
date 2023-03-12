package com.example.microgram.entity;

import lombok.Data;

@Data
public class Post {
    private int id;
    private String photo;
    private String description;
    private String postDate;
}
