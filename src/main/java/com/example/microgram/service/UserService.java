package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.example.microgram.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    private Connection conn;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=qwert";
        return DriverManager.getConnection(url);
    }

//    public String getDataSourceConn(){
//        DataSource dataSource = getDataSource();
//        try(Connection connection = dataSource.getConnection()){
//            if(connection.isValid(1)){
//                return "DataSource hikari config is all ok";
//            } else {
//                throw new SQLException();
//            }
//        } catch (SQLException e){
//            return e.getMessage();
//        }
//    }

//    private DataSource getDataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setUsername("postgres");
//        config.setPassword("qwert");
//        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=qwert");
//        return new HikariDataSource(config);
//    }

    private void init() throws  SQLException {
        conn = getNewConnection();
    }
    private int executeUpdate(String query) throws SQLException {
        init();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(query);
        return  result;
    }

    private void createUserTable() throws SQLException {
        String userEntryQuery = "insert into users_microgram " +
                "values(4, 'Aida_gal', 'aida@gmail.com', 'qwe', 1, 4)";

        executeUpdate(userEntryQuery);
    }
    public String shouldCreate(){
        try{
            createUserTable();
            conn.createStatement().execute("select * from users_microgram");
            return "All is ok";
        } catch (SQLException e){
            return e.getMessage();
        }
    }
    public List<User> listUsers(){
        return userDao.getUsers();
    }
    public String searchUserByName(String namE){
        try{
            conn.createStatement().execute("select user_name" +
                    " from users_microgram" +
                    "where lower(user_name) like '%namE%'");
            return "All is ok";
        } catch (SQLException e){
            return e.getMessage();
        }
    }
}
