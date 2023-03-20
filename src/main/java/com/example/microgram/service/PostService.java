package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private JdbcTemplate jdbcTemplate;

    public String updateTable(PostDto post) {
            String sql = "insert into posts(user_id, photo, description, post_date)\n" +
                    "values (?, ?, ?, ?);";
            jdbcTemplate.update(sql,
                    post.getUser_id(),
                    "Some photo",
                    post.getDescription(),
                    post.getPostDate());

            String updateUsersql = "insert into "
            return "Added new post";

    }

    public Optional getPostById(int entityId){
        String sql = "select * from posts where entity_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), entityId)
        ));
    }

    public List<Post> getOthersPosts(String email){
        String sql = "select * from posts\n" +
                "    join users on posts.user_id = users.user_id\n" +
                "    where users.email = ?;";
        return jdbcTemplate.query(sql, new PostMapper(), email);
    }

    public Optional getOthersPostsByFollowings(User user){
        String sql = "select * from posts\n" +
                "    join users on users.user_id = posts.user_id\n" +
                "    join follows on users.user_id = follows.user_following\n" +
                "    where users.email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), user.getUserId())
        ));
    }
}
