package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.post.PostDto;
import com.example.microgram.dto.post.PostDtoShow;
import com.example.microgram.dto.post.PostFrontDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private PostDao postDao;
    private UserDao userDao;

    public Optional<Post> findPostById(Long id){
        return postDao.postExistsID(id);
    }
    public PostFrontDto createNewPost(PostDto post, User user) {
        return postDao.newPost(post, user);
    }

    public List<PostDtoShow> getMyPosts(User user){
        List<Post> listPosts = postDao.getMyPosts(user);
        return listPosts.stream()
                .map(p -> PostDtoShow.buildPostDTO(p, user))
                .collect(Collectors.toList());
    }

    public List<PostDtoShow> getOthersPosts(User user){
        List<Post> listPosts = postDao.getOthersPosts(user.getUserId());
        return listPosts.stream()
                .map(p -> PostDtoShow.buildPostDTO(p, user))
                .collect(Collectors.toList());
    }

    public List<PostDtoShow> getOthersPostsByFollowings(User user){
        List<Post> listPosts = postDao.getPostsByFollowings(user.getUserId());

        List<PostDtoShow> posts = new ArrayList<>();
        for (Post p : listPosts) {
            Optional<User> userFollow = userDao.userExistsID(p.getUserId());
            posts.add(PostDtoShow.buildPostDTO(p, userFollow.get()));
        }
        return posts;
    }

    public ResultDto deleteThePost(Long post, User user) {

        Optional<Post> getPost = postDao.postExistsID(post);
        if (!getPost.isPresent()) {
            return ResultDto.builder()
                    .message("'" + post.toString() + "' does not exist.")
                    .build();
        }
        Optional<Post> getRightPost = postDao.myPostExists(post, user);
        if(!getRightPost.isPresent()){
            return ResultDto.builder()
                    .message("User of this post is another user!")
                    .build();
        }

        String mess = postDao.dropThePost(getPost.get());
        return ResultDto.builder()
                .message(mess)
                .build();
    }

    public List<PostFrontDto> allPosts() {
        return postDao.getAllPosts();
    }
}
