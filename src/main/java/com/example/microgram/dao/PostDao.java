package com.example.microgram.dao;

import com.example.microgram.dto.CommentFrontDto;
import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.PostDtoShow;
import com.example.microgram.dto.PostFrontDto;
import com.example.microgram.entity.Comment;
import com.example.microgram.entity.LikeType;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.CommentMapper;
import com.example.microgram.mappers.PostMapper;
import com.example.microgram.mappers.PostMapperUserName;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    public void createTable(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS posts\n" +
                "(\n" +
                "    post_id bigserial PRIMARY KEY,\n" +
                "    user_id bigint REFERENCES users(user_id),\n" +
                "    photo character varying(40),\n" +
                "    description text ,\n" +
                "    post_date_time timestamp\n" +
                ");");
    }
    public PostFrontDto newPost(PostDto post, User user){
        String sqlPost = "insert into posts(" +
                "user_id, " +
                "photo, " +
                "description, " +
                "post_date_time)\n" +
                "values (?, ?, ?, ?);";
        LocalDateTime ldt = LocalDateTime.now();
        int update = jdbcTemplate.update(sqlPost,
                user.getUserId(),
                post.getPhoto(),
                post.getDescription(),
                java.sql.Timestamp.valueOf(ldt));
        if(update == 1) {
            Optional<Post> createdPost = getIdenticalPost(post, user, ldt);
            if(createdPost.isPresent()) {
                return PostFrontDto.buildPost(createdPost.get(), user);
            }
        }
        return null;
    }

    private Optional<Post> getIdenticalPost(PostDto post, User user, LocalDateTime ldt) {
        String sql = "select * from posts " +
                "where user_id = ? " +
                "and photo = ? " +
                "and description = ? " +
                "and post_date_time = ? ;";
        Optional<Post> getPost = Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new PostMapper(),
                        user.getUserId(),
                        post.getPhoto(),
                        post.getDescription(),
                        ldt)
        ));
        return getPost;
    }

    public List<Post> getMyPosts(User user){
        String sql = "select * from posts\n" +
                "    where user_id = ?;";
        return jdbcTemplate.query(sql, new PostMapper(), user.getUserId());
    }
    public List<Post> getOthersPosts(Long userId){
        String sql = "select * from posts\n" +
                "    where posts.user_id  != ?;";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    // getPostsByUserName// if i am following
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
    public Optional<Post> myPostExists(Long postId, User user) {
        String sql = "select * from posts where user_id = ? and post_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new PostMapper(),
                        user.getUserId(),
                        postId)
        ));
    }
    public String dropThePost(Post post) {
        StringBuilder messageBack = new StringBuilder();

        String likesDeleted = deleteLikesOfThePostBeforeToDrop(post);
        messageBack.append(likesDeleted);
        String commentsDeleted = deleteCommentPostBeforeToDrop(post);
        messageBack.append(commentsDeleted);

        String sqlPost = "delete from posts where post_id = ?;";
        int updatePost = jdbcTemplate.update(sqlPost, post.getPostId());

        if(updatePost == 1){
            messageBack.append("Success! " + post + " has been deleted!");
        } else {
            messageBack.append("Could not delete the post.");
        }
        return messageBack.toString();
    }

    private String deleteCommentPostBeforeToDrop(Post post) {
        String sqlComment = "delete from comments_mic where post_id = ?;";
        int commentUpdate = jdbcTemplate.update(sqlComment, post.getPostId());
        if(commentUpdate == 1){
            return  "Success! comments from " + post +
                    " has been deleted!\n";
        }
        return "Could not delete comments.\n";
    }

    private String deleteLikesOfThePostBeforeToDrop(Post post) {
        String sqlLike = "delete from likes where entity_id = ? and like_type = ?;";

        int likeUpdate = jdbcTemplate.update(
                sqlLike,
                post.getPostId(),
                LikeType.POST.toString());
        if(likeUpdate == 1){
            return  "Success! Likes from " + post + " " +
                    " has been deleted!\n";
        }
        return  "Could not delete likes.\n";
    }

    public List<PostFrontDto> getAllPosts() {
        String sql = "select * from posts;";
        return jdbcTemplate.query(sql, new PostMapperUserName());
    }
}
