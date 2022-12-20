package com.revature.service;

import com.revature.persistence.UserDao;
import com.revature.pojos.User;

public class UserService {

    private UserDao userDao;

    public UserService (UserDao userDao) {
        this.userDao = userDao;
    }

    public Boolean authenticateUser(String email, String password) {
        return userDao.authenticateUser(email, password);
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
}
