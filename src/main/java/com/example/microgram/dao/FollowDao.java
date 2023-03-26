package com.example.microgram.dao;

import com.example.microgram.dto.FollowDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FollowDao extends BaseDao{

    public FollowDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<Follow> getFollows(){
        String sql = "select * from follows";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Follow.class));
    }

    public Optional getFollowers(User followers){
        String sql = "select * from follows where user_being_followed = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), followers)
        ));
    }

    public Optional getUsersFollowing(User following){
        String sql = "select * from follows where user_following = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), following)
        ));
    }

    public String toFollow(FollowDto data) {
        String sqlPost = "insert into follows(user_being_followed, " +
                "user_following, " +
                "follow_date)\n" +
                "values (?, ?, ?);";

        int update = jdbcTemplate.update(sqlPost,
                data.getUserBeingFollowed(),
                data.getUserFollowing(),
                convertToDateViaSqlDate(LocalDate.now()));
        if(update == 1) {
            return "Follow operation is succesful";
        } else {
            return "Can not create the post";
        }
    }

    public Optional<Follow> getIdenticalFollow(FollowDto data){
        String sql = "select * from follows where user_being_followed = ? and user_following = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(Follow.class),
                        data.getUserBeingFollowed(),
                        data.getUserFollowing())
        ));
    }
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public String unFollow(Follow data) {
        String sqlFollow = "delete from follows where follow_id = ?;";

        int update = jdbcTemplate.update(sqlFollow,
                data.getFollowId());
        if(update == 1) {
            return "UnFollow operation is succesful";
        } else {
            return "Can not create the post";
        }
    }
}
