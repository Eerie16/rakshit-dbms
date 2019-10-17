package com.example.demo.service;

import com.example.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.Dao.RoleDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Role;
import com.example.demo.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) {
        // System.out.print(username);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserName(userName);
        try {
            userDao.load(conn, user);
            System.out.println("/n/n"+userName+"/n/n231");
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(userName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println(username);
        Role role = new Role();
        role.setUserId(user.getUserId());
        List list=new ArrayList<>();
        try {
            list = roleDao.searchMatching(conn, role);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(list.size());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        for (Object i:list) {
            Role r=(Role)i;
            grantedAuthorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(),grantedAuthorities);
    }
}
