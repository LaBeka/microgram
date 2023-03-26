package com.example.microgram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Long commentId;
    private String commentText;
    private LocalDate commentDate;
    private Long userId;
    private Long postId;
}
