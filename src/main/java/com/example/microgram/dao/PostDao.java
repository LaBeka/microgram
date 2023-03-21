package com.example.microgram.dao;

import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public String updateTable(PostDto post, String sqlPost, String sqlUser){
        jdbcTemplate.update(sqlPost,
                0,
                post.getUserId(),
                "Some photo of user " + post.getUserId(),
                post.getDescription(),
                post.getPostDate());

        jdbcTemplate.update(sqlUser, post.getUserId());
        return "Added new post";
    }

    public List<Post> getOthersPosts(int userId, String sql){
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    public Optional getLikesPost(int postId, String sql){
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new PostMapper(), postId)
        ));
    }



}
