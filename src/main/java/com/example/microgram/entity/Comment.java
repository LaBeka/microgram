package com.example.microgram.entity;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private String commentText;
    private String commentDate;
}
