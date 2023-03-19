package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

//    public List<Comment> getComments(){
//        String sql = "select * from comment";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class));
//    }
//
//    private void dropTableIfExists(){
//        String sql = "DROP TABLE IF EXISTS comment;";
//        jdbcTemplate.execute(sql);
//    }
//    private void createTable(){
//        String sql = "CREATE TABLE comment (\n" +
//                "    entity_id serial primary key\n" +
//                "    user_id varchar(40) not null,\n" +
//                "    comment_text text not null,\n" +
//                "    like_date date,\n" +
//                ");";
//        jdbcTemplate.execute(sql);
//    }
    public void updateTable(Comment comment){
        String sql = "insert into comments_mic(user_id, comment_text, comment_date)\n" +
                "values (?, ?, ?);";
        jdbcTemplate.update(sql, comment.getUser_id(),
                comment.getComment_text(),
                comment.getComment_date());
    }
    public Optional getCommentById(int entityId){
        String sql = "select * from comments_mic where entity_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), entityId)
        ));
    }
}
