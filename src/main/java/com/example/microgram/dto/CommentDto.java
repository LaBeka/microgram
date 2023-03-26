package com.example.microgram.dto;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CommentDto {
    private String commentText;
    private Long userId;
    private Long postId;

    public String validateCommentData(){
        String messageBack = "";
        if(commentText == null || commentText.isBlank()){
            messageBack += "Comment text is not valid. ";
        }
        if(postId == null){
            messageBack += "Specify what you are commenting! ";
        }
        if(userId == null){
            messageBack += "Specify who is commenting! ";
        }
        return messageBack;
    }

    public static CommentDto buildCommentDTO(Comment comment){
        return CommentDto.builder()
                .commentText(comment.getCommentText())
                .userId(comment.getUserId())
                .build();
    }
}
