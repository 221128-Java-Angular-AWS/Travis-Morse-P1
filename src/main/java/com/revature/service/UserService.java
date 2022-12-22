package com.revature.service;

import com.revature.persistence.UserDao;
import com.revature.pojos.User;

import java.security.NoSuchAlgorithmException;

public class UserService {

    private UserDao userDao;

    public UserService (UserDao userDao) {
        this.userDao = userDao;
    }

    public String authenticateUser(User user) {
        return userDao.authenticateUser(user);
    }

    public void createNewUser(User user) {
        userDao.create(user);
    }

    public Boolean checkEmailAvailable(String email) { return userDao.checkEmailAvailable(email); }
    public User getUserByID(Integer userID) {
        return userDao.read(userID);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUserByID(Integer userID) {
        userDao.delete(userID);
    }

//    public void addHashedPassword(Integer userID, String password) throws NoSuchAlgorithmException { userDao.addHashedPassword(userID, password); }
}
