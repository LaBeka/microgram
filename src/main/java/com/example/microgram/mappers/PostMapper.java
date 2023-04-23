package com.example.microgram.mappers;

import com.example.microgram.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getLong("post_id"));
        post.setUserId(rs.getLong("user_id"));
        post.setPhoto(rs.getString("photo"));
        post.setDescription(rs.getString("description"));
        post.setPostDateTime(rs.getTimestamp("post_date_time").toLocalDateTime());
        return post;
    }
}
