package com.example.microgram.mappers;

import com.example.microgram.entity.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class CommentMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comm = new Comment();
        comm.setCommentId(rs.getLong("comment_id"));
        comm.setUserId(rs.getLong("user_id"));
        comm.setCommentText(rs.getString("comment_text"));
        comm.setCommentDate(rs.getDate("comment_date").toLocalDate());
        return comm;
    }
}
