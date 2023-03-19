package com.example.microgram.dao;

import com.example.microgram.entity.Like;
import com.example.microgram.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

//    public List<Like> getLikes(){
//        String sql = "select * from likes";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Like.class));
//    }
//
//    private void dropTableIfExists(){
//        String sql = "DROP TABLE IF EXISTS like;";
//        jdbcTemplate.execute(sql);
//    }
//    private void createTable(){
//        String sql = "CREATE TABLE like (\n" +
//                "    like_date date,\n" +
//                "    user_id varchar(40) not null,\n" +
//                "    type char not null,\n" +
//                "    entity_id serial primary key\n" +
//                ");";
//        jdbcTemplate.execute(sql);
//    }
    public void updateTable(Like like){
        String sql = "insert into likes(like_date, user_id, type_like)\n" +
                "values (?, ?, ?);";
        jdbcTemplate.update(sql, like.getLike_date(),
                like.getUser_id(),
                like.getType_like());
    }
    public Optional getLikeById(int entityId){
        String sql = "select * from likes where entity_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Like.class), entityId)
        ));
    }

    public Optional checkIfLiked(Post post){
        String sql = "select * from likes\n" +
                "    where type_like = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Like.class), post)
        ));
    }
}