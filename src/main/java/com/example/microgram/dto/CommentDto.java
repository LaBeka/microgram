package com.example.microgram.dto;

import com.example.microgram.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {
    private int id;
    private User user_id;
    private String commentText;
    private LocalDate commentDate;
}
