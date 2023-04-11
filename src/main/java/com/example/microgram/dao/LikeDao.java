package com.example.microgram.dao;

import com.example.microgram.dto.LIkeDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Like;
import com.example.microgram.entity.LikeType;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.CommentMapper;
import com.example.microgram.mappers.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public void createTable() {
        jdbcTemplate.execute("create table if not exists likes\n" +
                "(\n" +
                "    like_id    bigserial primary key,\n" +
                "    like_type    varchar(50) not null,\n" +
                "    user_id bigint references users(user_id),\n" +
                "    like_date date not null, \n" +
                "    entity_id  bigint not null\n" +
                ");");
    }
    public String putLikeOnThePost(LIkeDto data, User user) {
        String sqlLike = "insert into likes(like_type, user_id, like_date, entity_id)\n" +
                "values(?, ?, ?, ?)";

        int likeUpdate = jdbcTemplate.update(
                sqlLike,
                data.getLikeType().toString(),
                user.getUserId(),
                convertToDateViaSqlDate(LocalDate.now()),
                data.getEntityId()
        );
        if(likeUpdate == 1){
            return "Success!";
        }
        return "Unsuccess!";
    }
    public String unLikeThePost(Like lIke) {
        String sqlLike = "delete from likes\n" +
                "where like_id = ?;";
        int likeUpdate = jdbcTemplate.update(sqlLike, lIke.getLikeId());
        if(likeUpdate == 1){
            return "Success! " + lIke.toString() + " has been deleted!";
        }
        return "Unsuccess!";
    }
    public List<Like> getMyLikeOnPost(Long entityId, User user){
        String sql = "select * from likes\n" +
                "    where user_id = ? and entity_id = ? and like_type = ?;";
        return jdbcTemplate.query(
                sql,
                new LikeMapper(),
                user.getUserId(),
                entityId,
                LikeType.POST.toString());
    }
    public List<Like> getUserLikedThePost(
            Long userId,
            Long postId,
            LikeType type) {
        String sql = "select * from likes where user_id = ? and entity_id = ? and like_type = ?;";

        return jdbcTemplate.query(sql, new LikeMapper(), userId, postId, type.toString());
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Long getCountUsersLikesPost(Long postId){
        String sql = "select count(like_id) from likes where entity_id = ? and like_type = ? group by entity_id;";

        try {
            return jdbcTemplate.queryForObject(sql, Long.class, postId, LikeType.POST.toString());
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }

    public List<Comment> getUserLikedTheComment(
            Long userId,
            Long commentId,
            LikeType type) {
        String sql = "select * from likes where user_id = ? and entity_id = ? and like_type = ?;";

        return jdbcTemplate.query(sql, new CommentMapper(), userId, commentId, type.toString());
    }
    public String putLikeOnTheComment(LIkeDto data, User user) {
        String sqlLike = "insert into likes(like_type, user_id, like_date, entity_id)\n" +
                "values(?, ?, ?, ?)";

        int likeUpdate = jdbcTemplate.update(
                sqlLike,
                data.getLikeType().toString(),
                user.getUserId(),
                convertToDateViaSqlDate(LocalDate.now()),
                data.getEntityId()
        );
        if(likeUpdate == 1){
            return "Success!";
        }
        return "Unsuccess!";
    }

    public Long getCountUsersLikesComment(Long entityId){
        String sql = "select count(like_id) from likes where entity_id = ? and like_type = ? group by entity_id;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, entityId, LikeType.COMMENT.toString());
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }

    public List<Like> getMyLikeOnComment(Long entityId, User user){
        String sql = "select * from likes\n" +
                "    where user_id = ? and entity_id = ? and like_type = ?;";
        return jdbcTemplate.query(
                sql,
                new LikeMapper(),
                user.getUserId(),
                entityId,
                LikeType.COMMENT.toString());
    }
    public String unLikeTheComment(Like like) {
        String sqlLike = "delete from likes\n" +
                "where like_id = ?;";

        int likeUpdate = jdbcTemplate.update(sqlLike, like.getLikeId());

        if(likeUpdate == 1){
            return "Success! " + like + " has been deleted!";
        }
        return "Unsuccessful!";
    }
}