package com.example.microgram.service;

import com.example.microgram.dao.LikeDao;
import com.example.microgram.dao.PostDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.LIkeDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Like;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {
    private LikeDao likeDao;
    private PostDao postDao;
    private UserDao userDao;

    public ResultDto putLikeOnThePost(LIkeDto lIkeDto) {
        Optional<Post> getPost = postDao.postExistsID(lIkeDto.getPostId());
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + lIkeDto.getPostId() + "' does not exist.")
                    .build();
        }
        Optional<User> getUser = userDao.userExistsID(lIkeDto.getUserId());
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("User by '" + lIkeDto.getUserId() + "' does not exist.")
                    .build();
        }

        List<Like> ifUserLikedPost = likeDao.getLikePost(lIkeDto.getUserId(), lIkeDto.getPostId());
        if (ifUserLikedPost.size() != 0) {
            return ResultDto.builder()
                    .message("User '" + lIkeDto.getUserId() + "' has like on: '" + lIkeDto.getPostId() + "'.")
                    .build();
        }

        String message = likeDao.putLikeOnThePost(lIkeDto);
        return ResultDto.builder()
                .message(message)
                .build();
    }

    public ResultDto checkLikeOnThePost(Long postId){
        Optional<Post> getPost = postDao.postExistsID(postId);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + postId + "' does not exist.")
                    .build();
        }
        List<Like> gotPost = likeDao.getLikesPost(postId);
        if (gotPost.size() == 0) {
            return ResultDto.builder()
                    .message("Post '" + postId + "' does not have like on.")
                    .build();
        }
        Long count = likeDao.getUsersLikesPost(postId);
        return ResultDto.builder()
                .message("The post #" + postId + " has " + count + " likes on.")
                .build();
    }

    public ResultDto unLikeOnThePost(Long userId, Long postId) {
        Optional<Post> getPost = postDao.postExistsID(postId);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("Post by '" + postId + "' does not exist.")
                    .build();
        }
        Optional<User> getUser = userDao.userExistsID(userId);
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("User by '" + userId + "' does not exist.")
                    .build();
        }
        List<Like> gotLikedPost = likeDao.getLikePost(userId, postId);
        if (gotLikedPost.size() == 0) {
            return ResultDto.builder()
                    .message("Post '" + postId + "' does not have like on.")
                    .build();
        }

        String mess = likeDao.unLikeOnThePost(gotLikedPost.get(0));
        return ResultDto.builder()
                .message(mess)
                .build();
    }
}
