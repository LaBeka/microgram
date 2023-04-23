package com.example.microgram.dao;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.dto.CommentFrontDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public void createTable(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS comments_mic\n" +
                "(\n" +
                "    comment_id bigserial PRIMARY KEY,\n" +
                "    comment_text text,\n" +
                "    comment_datetime timestamp,\n" +
                "    user_id bigint REFERENCES users(user_id),\n" +
                "    post_id bigint REFERENCES posts(post_id)\n" +
                ");");
    }
    public CommentFrontDto addNewComment(CommentDto commentDto, User user){
        String sql = "insert into comments_mic(" +
                "comment_text, " +
                "comment_datetime, " +
                "user_id," +
                "post_id)\n" +
                "values (?, ?, ?, ?);";
        LocalDateTime ldt = LocalDateTime.now();
        int update = jdbcTemplate.update(sql,
                commentDto.getCommentText(),
                java.sql.Timestamp.valueOf(ldt),
                user.getUserId(),
                commentDto.getPostId());
        if(update == 1){
            Optional<Comment> createdComment = getIdenticalComment(commentDto, user, ldt);
            if(createdComment.isPresent()) {
                Comment comm = createdComment.get();
                return CommentFrontDto.builder()
                        .commentId(comm.getCommentId().toString())
                        .commentText(comm.getCommentText())
                        .commentDateTime(comm.getCommentDateTime().toString())
                        .userId(comm.getUserId().toString())
                        .postId(comm.getPostId().toString())
                        .build();
            }
        }
        return null;
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
    public Optional<Comment> getIdenticalComment(CommentDto data, User user, LocalDateTime ldt){
        String sql = "select * from comments_mic " +
                "where comment_text = ? " +
                "and user_id = ? " +
                "and post_id = ? " +
                "and comment_datetime = ?;";

        Optional<Comment> comment = Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new CommentMapper(),
                        data.getCommentText(),
                        user.getUserId(),
                        data.getPostId(),
                        ldt)
        ));
        return comment;
    }
    public Optional<Comment> getComment(CommentDto data, User user){
        String sql = "select * from comments_mic " +
                "where comment_text = ? " +
                "and user_id = ? " +
                "and post_id = ?;";

        Optional<Comment> comment = Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new CommentMapper(),
                        data.getCommentText(),
                        user.getUserId(),
                        data.getPostId())
        ));
        return comment;
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
