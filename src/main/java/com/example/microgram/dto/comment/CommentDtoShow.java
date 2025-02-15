//package com.example.microgram.dto;
//
//import com.example.microgram.entity.Comment;
//import com.example.microgram.entity.Post;
//import com.example.microgram.entity.User;
//import lombok.Builder;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//@Builder
//public class CommentDtoShow {
//    private String commentText;
//    private String post;
//    private String user;
//    private String commentDateTime;
//
//    public static CommentDtoShow buildCommentDTO(
//            Comment comment,
//            User user,
//            Post postId){
//        return CommentDtoShow.builder()
//                .commentText(comment.getCommentText())
//                .user(user.getAccountName())
//                .post(postId.getDescription())
//                .commentDateTime(comment.getCommentDateTime().toString())
//                .build();
//    }
//}
