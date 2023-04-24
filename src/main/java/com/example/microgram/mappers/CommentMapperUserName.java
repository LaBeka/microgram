package com.example.microgram.mappers;

import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.comment.CommentFrontDto;
import com.example.microgram.entity.User;
import com.example.microgram.utils.ApplicationContextHolder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CommentMapperUserName implements RowMapper<CommentFrontDto> {
    public CommentFrontDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommentFrontDto comm = new CommentFrontDto();
        comm.setCommentId(String.valueOf(rs.getLong("comment_id")));
        comm.setCommentText(rs.getString("comment_text"));
        comm.setCommentDateTime(String.valueOf(rs.getTimestamp("comment_datetime").toLocalDateTime()));

        UserDao userDao = ApplicationContextHolder.getContext().getBean(UserDao.class);
        Optional<User> userById = userDao.userExistsID(rs.getLong("user_id"));

        comm.setUserId(userById.get().getName());
        comm.setPostId(String.valueOf(rs.getLong("post_id")));
        return comm;
    }
}
