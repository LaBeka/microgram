package com.example.microgram.mappers;

import com.example.microgram.entity.Like;
import com.example.microgram.entity.LikeType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setLikeId(rs.getLong("like_id"));
        like.setLikeType(LikeType.valueOf(rs.getString("like_type")));
        like.setUserId(rs.getLong("user_id"));
        like.setLikeDate(rs.getDate("like_date").toLocalDate());
        like.setEntityId(rs.getLong("entity_id"));
        return like;
    }
}
