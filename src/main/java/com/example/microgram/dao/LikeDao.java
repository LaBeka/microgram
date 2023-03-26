package com.example.microgram.dao;

import com.example.microgram.dto.LIkeDto;
import com.example.microgram.entity.Like;
import com.example.microgram.mappers.LikeMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class LikeDao extends BaseDao{
    public LikeDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public String putLikeOnThePost(LIkeDto lIkeDto) {
        String sqlLike = "insert into likes(user_id, like_date, post_id)\n" +
                "values(?, ?, ?)";

        int likeUpdate = jdbcTemplate.update(
                sqlLike,
                lIkeDto.getUserId(),
                convertToDateViaSqlDate(LocalDate.now()),
                lIkeDto.getPostId()
        );
        if(likeUpdate == 1){
            return "Success!";
        } else{
            return "Unsuccess!";
        }
    }
    public String unLikeOnThePost(Like lIke) {
        String sqlLike = "delete from likes\n" +
                "where like_id = ?;";

        int likeUpdate = jdbcTemplate.update(sqlLike, lIke.getLikeId());
        if(likeUpdate == 1){
            return "Success! " + lIke.toString() + " has been deleted!";
        } else{
            return "Unsuccess!";
        }
    }
    public List<Like> getLikesPost(Long postId){
        String sql = "select * from likes\n" +
                "    join posts on posts.post_id = likes.post_id" +
                "    where posts.post_id = ?;";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Like.class), postId);
    }
    public List<Like> getLikePost(Long userId, Long postId) {
        String sql = "select * from likes where user_id = ? and post_id = ?;";
        List query = jdbcTemplate.query(sql, new LikeMapper(), userId, postId);
        return query;
    }
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Long getUsersLikesPost(Long postId){
        String sql = "select count(user_id) from likes where post_id = ? group by post_id;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, postId);
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }
}