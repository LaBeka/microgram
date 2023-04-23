package com.example.microgram.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommentFrontDto {
    private String commentId;
    private String commentText;
    private String commentDateTime;
    private String userId;
    private String postId;
}
