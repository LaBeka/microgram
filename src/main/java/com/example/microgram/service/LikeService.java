package com.example.microgram.service;

import com.example.microgram.dao.CommentDao;
import com.example.microgram.dao.LikeDao;
import com.example.microgram.dao.PostDao;
import com.example.microgram.dto.LIkeDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {
    private LikeDao likeDao;
    private PostDao postDao;
    private CommentDao commentDao;
    public ResultDto likeThePost(LIkeDto data, User user) {
        Optional<Post> getPost = postDao.postExistsID(data.getEntityId());

        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + data.getEntityId() + "' does not exist.")
                    .build();
        }

        List<Like> ifUserAlreadyLikedThePost = likeDao.getUserLikedThePost(user.getUserId(), data.getEntityId(), data.getLikeType());
        if (ifUserAlreadyLikedThePost.size() != 0) {
            return ResultDto.builder()
                    .message("User '" + user.getUserId() + "' has like on: '" + data.getEntityId() + data.getLikeType() + "'.")
                    .build();
        }

        String message = likeDao.putLikeOnThePost(data, user);
        return ResultDto.builder()
                .message(message)
                .build();
    }

    public ResultDto qtyOfLikesOfPost(Long entityId){
        Optional<Post> getPost = postDao.postExistsID(entityId);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + entityId + "' does not exist.")
                    .build();
        }

        Long count = likeDao.getCountUsersLikesPost(entityId);
        return ResultDto.builder()
                .message("The post #" + entityId + " has " + count + " likes on.")
                .build();
    }

    public ResultDto ifILikedThePost(Long entityId, User user){
        Optional<Post> getPost = postDao.postExistsID(entityId);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + entityId + "' does not exist.")
                    .isTrue(false)
                    .build();
        }
        List<Like> gotPost = likeDao.getMyLikeOnPost(entityId, user);
        if (gotPost.size() == 0) {
            return ResultDto.builder()
                    .message("User " + user + " have not liked this post yet'")
                    .isTrue(false)
                    .build();
        }
        return ResultDto.builder()
                .message("The post #" + entityId + " has my like on.")
                .isTrue(true)
                .build();
    }

    public ResultDto unLikeOnThePost(User user, Long postId) {
        Optional<Post> getPost = postDao.postExistsID(postId);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + postId + "' does not exist.")
                    .build();
        }

        List<Like> gotLikedPost = likeDao.getUserLikedThePost(user.getUserId(), postId, LikeType.POST);
        if (gotLikedPost.size() == 0) {
            return ResultDto.builder()
                    .message("I don not put like on this post")
                    .build();
        }

        String mess = likeDao.unLikeThePost(gotLikedPost.get(0));
        return ResultDto.builder()
                .message(mess)
                .build();
    }

    public ResultDto likeTheComment(LIkeDto data, User user) {
        Optional<Comment> getComment = commentDao.getCommentByID(data.getEntityId());
        if (!getComment.isPresent()) {
            return ResultDto.builder()
                    .message("Comment by '" + data.getEntityId() + "' does not exist.")
                    .build();
        }

        List<Comment> ifUserAlreadyLikedTheComment = likeDao.getUserLikedTheComment(
                user.getUserId(),
                data.getEntityId(),
                data.getLikeType());
        if (ifUserAlreadyLikedTheComment.size() != 0) {
            return ResultDto.builder()
                    .message("User '" + user + "' already has like on: '" + data.getEntityId() + data.getLikeType() + "'.")
                    .build();
        }

        String message = likeDao.putLikeOnTheComment(data, user);
        return ResultDto.builder()
                .message(message)
                .build();
    }

    public ResultDto qtyOfLikesOfComment(Long entityId){
        Optional<Comment> getComment = commentDao.getCommentByID(entityId);
        if (!getComment.isPresent()) {
            return ResultDto.builder()
                    .message("Comment by '" + entityId + "' does not exist.")
                    .build();
        }
        Long count = likeDao.getCountUsersLikesComment(entityId);
        return ResultDto.builder()
                .message("The comment #" + entityId + " has " + count + " likes on.")
                .build();
    }

    public ResultDto ifILikedTheComment(Long entityId, User user){
        Optional<Comment> getComm = commentDao.getCommentByID(entityId);
        if (!getComm.isPresent()) {
            return ResultDto.builder()
                    .message("Comment by '" + entityId + "' does not exist.")
                    .isTrue(false)
                    .build();
        }
        List<Like> gotComment = likeDao.getMyLikeOnComment(entityId, user);
        if (gotComment.size() == 0) {
            return ResultDto.builder()
                    .message("User " + user + " have not liked this comment yet'")
                    .isTrue(false)
                    .build();
        }
        return ResultDto.builder()
                .message("The comment #" + entityId + " has my like on.")
                .isTrue(true)
                .build();
    }

    public ResultDto unLikeOnTheComment(User user, Long entityId) {
        Optional<Comment> getComment = commentDao.getCommentByID(entityId);
        if (!getComment.isPresent()) {
            return ResultDto.builder()
                    .message("Comment by '" + entityId + "' does not exist.")
                    .build();
        }

        List<Like> gotLikedComment = likeDao.getUserLikedThePost(user.getUserId(), entityId, LikeType.COMMENT);
        if (gotLikedComment.size() == 0) {
            return ResultDto.builder()
                    .message("I do not put like on this comment")
                    .build();
        }

        String mess = likeDao.unLikeTheComment(gotLikedComment.get(0));
        return ResultDto.builder()
                .message(mess)
                .build();
    }
}
