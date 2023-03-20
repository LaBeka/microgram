package com.example.microgram.mappers;

import com.example.microgram.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPost_id(rs.getInt("post_id"));
        post.setEntity_id(rs.getInt("entity_id"));
        post.setUser_id(rs.getInt("user_id"));
        post.setPhoto(rs.getString("photo"));
        post.setDescription(rs.getString("description"));
        post.setPostDate(rs.getDate("post_date"));
        return post;
    }
}
