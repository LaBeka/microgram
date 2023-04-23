package com.example.microgram.mappers;

import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.PostFrontDto;
import com.example.microgram.entity.User;
import com.example.microgram.utils.ApplicationContextHolder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PostMapperUserName implements RowMapper<PostFrontDto> {
    @Override
    public PostFrontDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        PostFrontDto post = new PostFrontDto();
        post.setPostId(String.valueOf(rs.getLong("post_id")));

        UserDao userDao = ApplicationContextHolder.getContext().getBean(UserDao.class);
        Optional<User> userById = userDao.userExistsID(rs.getLong("user_id"));

        post.setUserName(userById.get().getName());
        post.setPhoto(rs.getString("photo"));
        post.setDescription(rs.getString("description"));
        post.setPostDateTime(String.valueOf(rs.getTimestamp("post_date_time").toLocalDateTime()));
        return post;
    }
}
