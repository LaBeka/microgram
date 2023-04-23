package com.example.microgram.dto;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private String commentText;
    private Long postId;

    public String validateCommentData(){
        String messageBack = "";
        if(commentText == null || commentText.isBlank()){
            messageBack += "Comment text is not valid. ";
        }
        if(postId == null){
            messageBack += "Specify what you are commenting! ";
        }
        return messageBack;
    }

}
