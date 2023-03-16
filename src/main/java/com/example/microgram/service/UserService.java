package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    private Connection conn;

    public UserService(UserDao userDao) throws SQLException{
        this.userDao = userDao;
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

    private String findUser(String subject, String query){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, subject);
            if (preparedStatement.execute()) {
                return "All good";
            } else {
                throw new SQLException();
            }
        } catch (SQLException e){
            return e.getMessage();
        }
    }

    public void searchUserByName(String user_name)  {
        String query = "select * from users_microgram where user_name = ?";
        findUser(user_name, query);
    }

    public void searchUserByAccount(String account_name)  {
        String query = "select * from users_microgram where accountname = ?";
        findUser(account_name, query);
    }
    public void searchUserByEmail(String email){
        String query = "select * from users_microgram where email = ?";
        findUser(email, query);
    }
    public String ifUserExists(String email){
        try{
            String query = "select * from users_microgram where email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            if (preparedStatement.execute()) {
                return "There is user with email " + email;
            } else {
                throw new SQLException("There is no user with thus email");
            }
        } catch (SQLException e){
            return e.getMessage();
        }
    }

}
