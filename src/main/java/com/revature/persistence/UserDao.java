package com.revature.persistence;

import com.revature.pojos.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Connection connection;

    private UserDao() {
        this.connection = ConnectionManager.getConnection();
    }

    public void create(User user) {
        try {
            String sql = "INSERT INTO users (first_name, last_name, role, email, password) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public User read(Integer userID) {
        User user = new User();

        try {
            String sql = "SELECT * FROM users WHERE user_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);

            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                user.setUserID(results.getInt("user_id"));
                user.setFirstName(results.getString("first_name"));
                user.setLastName(results.getString("last_name"));
                user.setRole(results.getString("role"));
                user.setEmail(results.getString("email"));
                user.setPassword(results.getString("password"));
            }
        } catch (SQLException e) {
            //TODO: update exception handling
            throw new RuntimeException(e);
        }

        return user;
    }

    public void update(User user) {
        try {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, role = ?, email = ?, password = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer user_id) {
        try {
            String sql = "DELETE FROM users WHERE user_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }
    }
}

