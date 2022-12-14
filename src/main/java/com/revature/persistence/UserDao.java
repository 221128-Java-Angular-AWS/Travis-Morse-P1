package com.revature.persistence;

import com.revature.pojos.User;

//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Connection connection;

    public UserDao() {
        this.connection = ConnectionManager.getConnection();
    }

    public String authenticateUser(User user) {
        // TODO: hash passwords
        try {
            String sql = "SELECT * FROM users WHERE email = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            ResultSet results = pstmt.executeQuery();
            results.next();
            // TODO: create userNotFound and incorrectPassword exceptions, and check results.next()
            if (results.getString("password").equals(user.getPassword())) {
                return results.getString("role");
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void create(User user) {
        try {
            String sql = "INSERT INTO users (first_name, last_name, role, email, password) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, "employee");
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public Boolean checkEmailAvailable(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ? LIMIT 1;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            //TODO: improve exception handling
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
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }

        return user;
    }

    public void update(User user) {
        try {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, role = ?, email = ?, password = ? WHERE user_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setInt(6, user.getUserID());
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

//    public void addHashedPassword(Integer userID, String password) throws NoSuchAlgorithmException {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            byte[] hashedPassword = md.digest("freebird".getBytes(StandardCharsets.UTF_8));
//            String sql = "UPDATE users SET hashed_password = ? WHERE user_id = ?;";
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setString(1, hashedPassword.toString());
//            pstmt.setInt(2, userID);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}

