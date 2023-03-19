package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FollowDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Follow> getFollows(){
        String sql = "select * from follows";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Follow.class));
    }

//    private void dropTableIfExists(){
//        String sql = "DROP TABLE IF EXISTS follow;";
//        jdbcTemplate.execute(sql);
//    }
//    private void createTable(){
//        String sql = "CREATE TABLE follow (\n" +
//                "    follow_id serial primary key,\n" +
//                "    user_being_followed varchar(40) not null,\n" +
//                "    user_following varchar(40) not null,\n" +
//                "    follow_date date,\n" +
//                "    count_followers bigint primary key\n" +
//                ");";
//        jdbcTemplate.execute(sql);
//    }
//    public void updateTable(Follow follow){
//        String sql = "insert into follows(user_being_followed, user_following, follow_date)\n" +
//                "values (?, ?, ?, ?);";
//        jdbcTemplate.update(sql, follow.getUserBeingFollowed(),
//                follow.getUserFollowing(),
//                follow.getFollowDate());
//    }

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
}
