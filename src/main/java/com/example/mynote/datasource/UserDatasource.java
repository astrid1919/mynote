package com.example.mynote.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.example.mynote.model.User;

public class UserDatasource {
    private static final String URL = "jdbc:mysql://localhost:3306/mynoteDB";
    private Connection connection;


    public UserDatasource() {
        try {
            connection = DriverManager.getConnection(URL, "root", "123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        try {
            Statement statement = connection.createStatement();
            var resultSet = statement.executeQuery(
                "SELECT id, username, password, email FROM tbUser WHERE username = '" + username + "' AND password = '" + password + "'"
            );
            if (resultSet.next()) {
                return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return null;
    }

    public boolean register(User user) {
        try {
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(
                "INSERT INTO tbUser (username, email, password) VALUES ('" + user.getUsername() + "', '" + user.getEmail() + "', '" + user.getPassword() + "')"
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }
}
