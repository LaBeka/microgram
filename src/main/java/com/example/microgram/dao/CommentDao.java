package com.example.microgram.dao;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public String addNewComment(CommentDto comment, User user){
        String sql = "insert into comments_mic(" +
                "comment_text, " +
                "comment_date, " +
                "user_id," +
                "post_id)\n" +
                "values (?, ?, ?, ?);";
        int update = jdbcTemplate.update(sql,
                comment.getCommentText(),
                java.sql.Date.valueOf(LocalDate.now()),
                user.getUserId(),
                comment.getPostId());
        if(update == 1){
            return "Added new comment: " + comment;
        }
        return "Unsuccessful";
    }
    public Optional<Comment> getUsersCommentById(Long commentId, User user){
        String sql = "select * from comments_mic " +
                "where user_id = ? " +
                "and comment_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new CommentMapper(),
                        user.getUserId(),
                        commentId)
        ));
    }
    public Optional<Comment> getIdenticalComment(CommentDto data, User user){
        String sql = "select * from comments_mic " +
                "where comment_text in (?) " +
                "and user_id = ? " +
                "and post_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new CommentMapper(),
                        data.getCommentText(),
                        user.getUserId(),
                        data.getPostId())
        ));
    }

    public String unComment(Comment comment) {
        String sql = "delete from comments_mic \n" +
                "where comment_id = ?;";
        int update = jdbcTemplate.update(sql, comment.getCommentId());

        if(update == 1){
            return "Successful delete!";
        }
        return "Unsuccessful delete";
    }
    public Optional<Comment> getCommentByID(Long commentId){
        String sql = "select * from comments_mic " +
                "where comment_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new CommentMapper(), commentId)
        ));
    }

    public String updateTextComment(Comment comment, String text) {
        String sql = "update comments_mic\n" +
                "set comment_text = ?\n" +
                "where comment_id = ?;";
        int update = jdbcTemplate.update(sql, text, comment.getCommentId());
        if(update == 1){
            return "Success to update text of comment";
        }
        return "Unsuccessful";
    }
}
