package com.example.microgram.mappers;

import com.example.microgram.entity.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comm = new Comment();
        comm.setCommentId(rs.getLong("comment_id"));
        comm.setCommentText(rs.getString("comment_text"));
        comm.setCommentDateTime(rs.getTimestamp("comment_datetime").toLocalDateTime());
        comm.setUserId(rs.getLong("user_id"));
        comm.setPostId(rs.getLong("post_id"));
        return comm;
    }
}
