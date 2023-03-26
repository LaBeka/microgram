package com.example.microgram.dao;

import com.example.microgram.dto.PostDto;
import com.example.microgram.entity.Post;
import com.example.microgram.mappers.PostMapper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostDao extends BaseDao{
    public PostDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public String newPost(PostDto post){
        String sqlPost = "insert into posts(" +
                "user_id, " +
                "photo, " +
                "description, " +
                "post_date)\n" +
                "values (?, ?, ?, ?);";

        int update = jdbcTemplate.update(sqlPost,
                post.getUserId(),
                "Some photo of user " + post.getUserId(),
                post.getDescription(),
                convertToDateViaSqlDate(post.getPostDate()));
        if(update == 1) {
            return "Added new post";
        } else {
            return "Can not create the post";
        }
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public List<Post> getOthersPosts(Long userId){
        String sql = "select * from posts\n" +
                "    where posts.user_id  != ?;";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    public List<Post> getPostsByFollowings(Long userId){
        String sql = "select * from posts\n" +
                "    join follows on posts.user_id = follows.user_being_followed" +
                "    where follows.user_following = ?;";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    public Optional<Post> postExistsID(Long postId) {
        String sql = "select * from posts where post_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new PostMapper(), postId)
        ));
    }
    public Optional<Post> postExists(PostDto data) {
        String sql = "select * from posts where user_id = ? and description = ? and post_date = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new PostMapper(),
                        data.getUserId(),
                        data.getDescription(),
                        convertToDateViaSqlDate(data.getPostDate()))
        ));
    }
    public String dropThePost(Post post) {
        String messageBack = "";

        String likesDeleted = deleteLikesOfThePostBeforeToDrop(post);
        String commentsDeleted = deleteCommentPostBeforeToDrop(post);
        messageBack += likesDeleted;
        messageBack += commentsDeleted;

        String sqlPost = "delete from posts where post_id = ?;";
        int updatePost = jdbcTemplate.update(sqlPost, post.getPostId());

        if(updatePost == 1){
            messageBack += "Success! " + post.toString()+ " has been deleted!";
        } else {
            messageBack += "Could not delete the post.\n";
        }
        return messageBack;
    }

    private String deleteCommentPostBeforeToDrop(Post post) {
        String sqlComment = "delete from comments_mic where post_id = ?;";
        int commentUpdate = jdbcTemplate.update(sqlComment, post.getPostId());
        if(commentUpdate == 1){
            return  "Success! comments from " + post.toString() +
                    " has been deleted!\n";
        }else {
            return  "Could not delete comments.\n";
        }
    }

    private String deleteLikesOfThePostBeforeToDrop(Post post) {
        String sqlLike = "delete from likes where post_id = ?;";

        int likeUpdate = jdbcTemplate.update(
                sqlLike,
                post.getPostId());
        if(likeUpdate == 1){
            return  "Success! Likes from " + post.toString()+ " " +
                    " has been deleted!\n";
        } else {
            return  "Could not delete likes.\n";
        }
    }
}
