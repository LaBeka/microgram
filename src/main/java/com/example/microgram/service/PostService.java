package com.example.microgram.service;

import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final JdbcTemplate jdbcTemplate;

    public String updateTable(PostDto post) {
            String sql = "insert into posts(entity_id, user_id, photo, description, post_date)\n" +
                    "values (?, ?, ?, ?, ?);";
            jdbcTemplate.update(sql,
                    0,
                    post.getUser_id(),
                    "Some photo",
                    post.getDescription(),
                    post.getPostDate());

            String updateUserSql = "update users\n" +
                    "set post_quantity = post_quantity + 1\n" +
                    "where user_id = ?";
            jdbcTemplate.update(updateUserSql, post.getUser_id());

            return "Added new post";
    }

    public Optional getPostById(int entityId){
        String sql = "select * from posts where entity_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), entityId)
        ));
    }

    public List<Post> getOthersPosts(int userId){
        String sql = "select * from posts\n" +
                "    where posts.user_id not in (?);";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    public List<Post> getOthersPostsByFollowings(int userId){
        String sql = "select * from posts\n" +
                "    join follows on posts.user_id = follows.user_following" +
                "    where follows.user_following not in (?);";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }
    public Optional<Post> checkLikeOnPost(int postId){
        String sql = "select * from posts\n" +
                "    join likes on posts.entity_id = likes.entityid" +
                "    where posts.post_id = ?" +
                "    and posts.entity_id in (likes.entityid);";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new PostMapper(), postId)
        ));
    }

    public String putLikeOnThePost(MultiValueMap<String, String> formData) {
        int postId = Integer.parseInt(String.valueOf(formData.get("postId")));

        String sql = "select * from posts\n" +
                "    join likes on posts.entity_id = likes.entityid" +
                "    where posts.post_id = ?" +
                "    and posts.entity_id in (likes.entityid);";
        return "";
    }
}
