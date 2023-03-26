package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private PostDao postDao;
    private UserDao userDao;

    public ResultDto createNewPost(PostDto post) {
        String validateErrors = post.validatePostData();
        if (!validateErrors.isEmpty()) {
            return ResultDto.builder()
                    .message(validateErrors)
                    .build();
        }

        Optional<User> getUser = userDao.userExistsID(post.getUserId());
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("\nUser by '" + post.getUserId() + "' does not exist.\n")
                    .build();
        }
        String result = postDao.newPost(post);

        return ResultDto.builder()
                .message(result)
                .build();
    }

    public List<PostDto> getOthersPosts(Long userId){
        Optional<User> getUser = userDao.userExistsID(userId);
        if (!getUser.isPresent()) {
            return null;
        }

        List<Post> listPosts = postDao.getOthersPosts(userId);
        return listPosts.stream()
                .map(p -> PostDto.buildPostDTO(p))
                .collect(Collectors.toList());
    }

    public List<PostDto> getOthersPostsByFollowings(Long userId){
        Optional<User> getUser = userDao.userExistsID(userId);
        if (!getUser.isPresent()) {
            return null;
        }

        List<Post> listPosts = postDao.getPostsByFollowings(userId);
        return listPosts.stream()
                .map(p -> PostDto.buildPostDTO(p))
                .collect(Collectors.toList());
    }

    public ResultDto deleteThePost(PostDto postDto) {
        String validateErrors = postDto.validatePostData();
        if (!validateErrors.isEmpty()) {
            return ResultDto.builder()
                    .message(validateErrors)
                    .build();
        }

        Optional<Post> getPost = postDao.postExists(postDto);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("'" + postDto.toString() + "' does not exist.\n")
                    .build();
        }
        Optional<User> getUser = userDao.userExistsID(postDto.getUserId());
        if (!getUser.isPresent()) {
            return ResultDto.builder()
                    .message("'" + postDto.getUserId() + "' does not exist.\n")
                    .build();
        }

        if(getUser.get().getUserId() != getPost.get().getUserId()){
            return ResultDto.builder()
                    .message("User of this post is another user!")
                    .build();
        }

        String mess = postDao.dropThePost(getPost.get());
        return ResultDto.builder()
                .message(mess)
                .build();
    }
}
