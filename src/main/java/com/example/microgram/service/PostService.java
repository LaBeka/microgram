package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    private PostDto buildPost(Post postfound) {
        return PostDto.builder()
                .userId(postfound.getUserId())
                .photo(postfound.getPhoto())
                .description(postfound.getDescription())
                .build();
    }
    public String addNewPost(PostDto post) {
        String sqlpost = "insert into posts(like_id, " +
                "user_id, " +
                "photo, " +
                "description, " +
                "post_date)\n" +
                "values (?, ?, ?, ?, ?);";

        String sqlUser = "update users\n" +
                "set post_quantity = post_quantity + 1\n" +
                "where user_id = ?";

        return postDao.updateTable(post, sqlpost, sqlUser);
    }

//    public Optional getPostById(int entityId){
//        String sql = "select * from posts where entity_id = ?;";
//
//        return Optional.ofNullable(DataAccessUtils.singleResult(
//                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), entityId)
//        ));
//    }

    public List<PostDto> getOthersPosts(int userId){
        String sql = "select * from posts\n" +
                "    where posts.user_id not in (?);";
        List<Post> listPosts = postDao.getOthersPosts(userId, sql);
        return listPosts.stream()
                .map(p -> buildPost(p))
                .collect(Collectors.toList());
    }

    public List<PostDto> getOthersPostsByFollowings(int userId){
        String sql = "select * from posts\n" +
                "    join follows on posts.user_id = follows.user_following" +
                "    where follows.user_following not in (?);";
        List<Post> listPosts = postDao.getOthersPosts(userId, sql);
        return listPosts.stream()
                .map(p -> buildPost(p))
                .collect(Collectors.toList());
    }

    public Optional checkLikeOnPost(int postId){
        String sql = "select * from posts\n" +
                "    join likes on posts.like_id = likes.like_id" +
                "    where posts.post_id = ?" +
                "    and posts.like_id in (likes.like_id);";
        return postDao.getLikesPost(postId, sql);
    }

    public String putLikeOnThePost(MultiValueMap<String, String> formData) {
        int postId = Integer.parseInt(String.valueOf(formData.get("postId")));
        Date likeDate = (Date) formData.get("likeDate");

        String sqlNewLike = "insert into likes(user_id, like_date)\n" +
                "values()";

        String sqlPosts =  "update posts\n" +
                "set like_id = like_id + 1\n" +
                "where post_id = ?";


//                String p = "update posts\n" +
//                "    join likes on posts.like_id = likes.like_id" +
//                "    where posts.post_id = ?" +
//                "    and posts.like_id in (likes.like_id);";
        return "";
    }
}
