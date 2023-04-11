package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Follow> getFollows(){
        String sql = "select * from follows";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Follow.class));
    }

    public List<User> getUsersFollowings(User user){
        String sql = "select * from users\n" +
                "join follows on users.user_id = follows.user_being_followed\n" +
                "where follows.user_following = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), user.getUserId());
    }

    public List<User> getUsersFollowers(User user){
        String sql = "select * from users\n" +
                "join follows on users.user_id = follows.user_following\n" +
                "where follows.user_being_followed = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), user.getUserId());
    }

    public String toFollow(User beingFollowed, User follower) {
        String sqlPost = "insert into follows(user_being_followed, " +
                "user_following, " +
                "follow_date)\n" +
                "values (?, ?, ?);";

        int update = jdbcTemplate.update(sqlPost,
                beingFollowed.getUserId(),
                follower.getUserId(),
                java.sql.Date.valueOf(LocalDate.now()));
        if(update == 1) {
            return "Follow operation is succesful";
        }
        return "Can not create the follow";
    }

    public Optional<Follow> alreadyFollowing(User beingFollowed, User follower){
        String sql = "select * from follows where user_being_followed = ? and user_following = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(Follow.class),
                        beingFollowed.getUserId(),
                        follower.getUserId())
        ));
    }

    public String unFollow(User beingFollowed, User follower) {
        String sql = "delete from follows where user_being_followed = ? and user_following = ?;";
        int update = jdbcTemplate.update(sql,
                beingFollowed.getUserId(),
                follower.getUserId());

        if(update == 1) {
            return "UnFollow operation is successful";
        }
        return "Unsuccessful operation to unFollow!";
    }
}
