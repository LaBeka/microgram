package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import com.example.microgram.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;
    private Connection conn;

    public PostService(PostDao postDao) throws SQLException {
        this.postDao = postDao;
        this.conn = getNewConnection();
    }

    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=qwert";
        return DriverManager.getConnection(url);
    }
    public String getDataSourceConn(){
        DataSource dataSource = getDataSource();
        try(Connection connection = dataSource.getConnection()){
            if(connection.isValid(1)){
                return "DataSource hikari config is all ok";
            } else {
                throw new SQLException();
            }
        } catch (SQLException e){
            return e.getMessage();
        }
    }

    private DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("qwert");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=qwert");
        return new HikariDataSource(config);
    }

    public String selectOtherPosts(User user){
        String user_email = user.getEmail();
        try{
            String query = "select * from post_microgram as p " +
                    "inner join users_microgram as u on p.user_id = u.user_id " +
                    "where u.email not in (?)";
            PreparedStatement ps1 = conn.prepareStatement(query);
            ps1.setString(1, user_email);
            if (ps1.execute()) {
                return "All is ok";
            } else {
                throw new SQLException("There is no posts");
            }
        } catch (SQLException e){
            return e.getMessage();
        }
    }
    public String postsByFollowing(User user){
        String user_email = user.getEmail();
        try{
            String query = "select * from post_microgram as p " +
                    "join users_microgram as u on p.user_id = u.user_id " +
                    "join follow_microgram as f on u.user_id = f.userfollowing" +
                    "where u.email not in (?)";
            PreparedStatement ps1 = conn.prepareStatement(query);
            ps1.setString(1, user_email);
            if (ps1.execute()) {
                return "All is ok";
            } else {
                throw new SQLException("There is no posts");
            }
        } catch (SQLException e){
            return e.getMessage();
        }
    }
}
