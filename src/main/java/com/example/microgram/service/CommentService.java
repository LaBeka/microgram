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

    public ResultDto addNewComment(CommentDto data, User user){
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

        Optional<Comment> ifThisCommentExists = commentDao.getIdenticalComment(data, user);
        if (ifThisCommentExists.isPresent()) {
            return ResultDto.builder()
                    .message("This comment already exists.")
                    .build();
        }

        String result = commentDao.addNewComment(data, user);
        return ResultDto.builder()
                .message(result)
                .build();
    }

    public ResultDto unComment(CommentDto data, User user){
        Optional<Post> getPost = postDao.postExistsID(data.getPostId());
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + data.getPostId() + "' does not exist. ")
                    .build();
        }

        Optional<Comment> getIdenticalComment = commentDao.getIdenticalComment(data, user);
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

    public ResultDto updateCommentText(Long commentId, User user, String text) {
        Optional<Comment> getIdenticalComment = commentDao.getUsersCommentById(commentId, user);
        if (!getIdenticalComment.isPresent()) {
            return ResultDto.builder()
                    .message("This comment by this comment id does not exist. Or User id is not correct!")
                    .build();
        }
        String message = commentDao.updateTextComment(getIdenticalComment.get(), text);
        return ResultDto.builder()
                .message(message)
                .build();
    }
}
