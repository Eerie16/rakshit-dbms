package com.example.demo.controllers;

import java.security.Principal;
import java.sql.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.NotFoundException;
import com.example.demo.Dao.RoleDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;
import com.example.demo.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user/registration")
    public String registration(Model model) {
        return "redirect:/client/new";
    }

    @GetMapping("/employee/registration")
    public String empregistre(Model model) {
        return "redirect:/employee/new";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        System.out.println(bCryptPasswordEncoder.encode("superuser"));
        return "login";
    }

    @GetMapping({ "/welcome" })
    public String welcome(Model model) {
        return "welcome";
    }



}
