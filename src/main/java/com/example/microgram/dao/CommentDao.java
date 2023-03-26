package com.example.microgram.dao;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.entity.Comment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CommentDao extends BaseDao{
    public CommentDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public String addNewComment(CommentDto comment){
        String sql = "insert into comments_mic(" +
                "comment_text, " +
                "comment_date, " +
                "user_id," +
                "post_id)\n" +
                "values (?, ?, ?, ?);";
        int update = jdbcTemplate.update(sql,
                comment.getCommentText(),
                convertToDateViaSqlDate(LocalDate.now()),
                comment.getUserId(),
                comment.getPostId());
        if(update == 1){
            return "Added new comment: " + comment.toString();
        } else{
            return "Unsuccess";
        }
    }
    public Optional<Comment> ifCommentExists(CommentDto data){
        String sql = "select * from comments_mic " +
                "where comment_text in (?) " +
                "and user_id = ? " +
                "and post_id = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(Comment.class),
                        data.getCommentText(),
                        data.getUserId(),
                        data.getPostId())
        ));
    }
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public String unComment(Comment comment) {
        String sql = "delete from comments_mic \n" +
                "where comment_id = ?;";
        int update = jdbcTemplate.update(sql, comment.getCommentId());
        if(update == 1){
            return "Successful delete!";
        } else {
            return "Unsuccessful delete";
        }
    }
}
