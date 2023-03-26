package com.example.microgram.service;

import com.example.microgram.dao.CommentDao;
import com.example.microgram.dao.PostDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.CommentDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentDao commentDao;
    private UserDao userDao;
    private PostDao postDao;

    public ResultDto addNewComment(CommentDto data){
        String error = data.validateCommentData();
        if(!error.isEmpty()){
            return ResultDto.builder()
                    .message(error)
                    .build();
        }
        Optional<Post> getPost = postDao.postExistsID(data.getPostId());
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + data.getPostId() + "' does not exist. ")
                    .build();
        }
        Optional<User> getUser = userDao.userExistsID(data.getUserId());
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("\nUser by '" + data.getUserId() + "' does not exist.\n")
                    .build();
        }

        Optional<Comment> getIdenticalComment = commentDao.ifCommentExists(data);
        if (getIdenticalComment.isPresent()) {
            return ResultDto.builder()
                    .message("This comment already exists.")
                    .build();
        }

        String result = commentDao.addNewComment(data);
        return ResultDto.builder()
                .message(result)
                .build();
    }

    public ResultDto unComment(CommentDto data){
        Optional<Post> getPost = postDao.postExistsID(data.getPostId());
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + data.getPostId() + "' does not exist. ")
                    .build();
        }
        Optional<User> getUser = userDao.userExistsID(data.getUserId());
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("\nUser by '" + data.getUserId() + "' does not exist.\n")
                    .build();
        }

        Optional<Comment> getIdenticalComment = commentDao.ifCommentExists(data);
        if (!getIdenticalComment.isPresent()) {
            return ResultDto.builder()
                    .message("This comment does not exist. ")
                    .build();
        }
        String result = commentDao.unComment(getIdenticalComment.get());
        return ResultDto.builder()
                .message(result)
                .build();
    }
}
