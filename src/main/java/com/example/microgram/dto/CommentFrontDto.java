package com.example.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentFrontDto {
    private String commentId;
    private String commentText;
    private String commentDateTime;
    private String userId;
    private String postId;
}
