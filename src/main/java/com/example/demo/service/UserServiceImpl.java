package com.example.demo.service;

import java.sql.*;

import com.example.NotFoundException;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userDao.create(conn, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void updatepassword(int Id, String newpassword) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        newpassword = bCryptPasswordEncoder.encode(newpassword);
        User user = new User();
        user.setUserId(Id);
        try {
            userDao.load(conn, user);
        } catch (NotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        user.setPassword(newpassword);
        try {
            userDao.save(conn, user);
        } catch (NotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public User findByUsername(String username) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserName(username);
        try {
            userDao.load(conn, user);
        } catch (NotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean findCount(String username) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserName(username);
        try {
            userDao.load(conn, user);
            return false;
        } catch (NotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            return true;
        }
    }
}
