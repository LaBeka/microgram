package com.example.microgram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Long commentId;
    private String commentText;
    private LocalDateTime commentDateTime;
    private Long userId;
    private Long postId;

}
