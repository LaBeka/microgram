package com.example.microgram.dao;

import com.example.microgram.entity.Like;
import com.example.microgram.entity.Post;
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
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

//    public List<Post> getPosts(){
//        String sql = "select * from post";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
//    }
//
//    private void dropTableIfExists(){
//        String sql = "DROP TABLE IF EXISTS post;";
//        jdbcTemplate.execute(sql);
//    }
//    private void createTable(){
//        String sql = "CREATE TABLE post (\n" +
//                "    entity_id serial primary key,\n" +
//                "    user_id varchar(40) not null,\n" +
//                "    photo varchar(40),\n" +
//                "    description text,\n" +
//                "    post_date date\n" +
//                ");";
//        jdbcTemplate.execute(sql);
//    }
    public void updateTable(Post post){
        String sql = "insert into posts(user_id, photo, description, post_date)\n" +
                "values (?, ?, ?, ?);";
        jdbcTemplate.update(sql, post.getUser_id(),
                post.getPhoto(),
                post.getDescription(),
                post.getPost_date());
    }

    public Optional getPostById(int entityId){
        String sql = "select * from posts where entity_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), entityId)
        ));
    }

    public Optional getOthersPosts(User user){
        String sql = "select * from posts\n" +
                "    join users on posts.user_id = users.user_id\n" +
                "    where users.email not in (?);";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), user.getEmail())
        ));
    }

    public Optional getOthersPostsByFollowings(User user){
        String sql = "select * from posts\n" +
                "    join users on users.user_id = posts.user_id\n" +
                "    join follows on users.user_id = follows.user_following\n" +
                "    where users.email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), user.getUserId())
        ));
    }
}
