package com.example.microgram.service;

import com.example.microgram.dao.LikeDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.entity.Like;
import com.example.microgram.entity.Post;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

public class LikeService {
    @Autowired
    private LikeDao likeDao;
    private Connection conn;

    public LikeService(LikeDao likeDao) throws SQLException {
        this.likeDao = likeDao;
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

    public boolean checkIsLiked(Post post_microgram){
        int post = post_microgram.getId();
        boolean isTrue = false;
        try{
            String query = "select * from post_microgram as p " +
                    "join like_microgram as l on p.id = l.entityid " +
                    "where p.id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, post);
            if (ps.execute()) {
                isTrue = true;
            } else {
                isTrue = false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return isTrue;
    }

}
