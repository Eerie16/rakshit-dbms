package com.example.demo.controllers;

import com.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.eclipse.jdt.internal.compiler.ast.Clinit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.sql.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Client;
import com.example.demo.models.ClientOrder;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeEmail;
import com.example.demo.models.EmployeePhoneNo;
import com.example.demo.models.MadeOf;
import com.example.demo.models.Manufactures;
import com.example.demo.models.Plant;
import com.example.demo.models.Products;
import com.example.demo.models.RawMaterial;
import com.example.demo.models.Role;
import com.example.demo.models.Stores;
import com.example.demo.models.Supplier;
import com.example.demo.models.Supplies;
import com.example.demo.models.SupplyOrder;
import com.example.demo.models.User;
import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.example.NotFoundException;
import com.example.demo.Dao.ClientDao;
import com.example.demo.Dao.ClientOrderDao;
import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.EmployeeEmailDao;
import com.example.demo.Dao.EmployeePhoneNoDao;
import com.example.demo.Dao.MadeOfDao;
import com.example.demo.Dao.ManufacturesDao;
import com.example.demo.Dao.PlantDao;
import com.example.demo.Dao.ProductsDao;
import com.example.demo.Dao.RawMaterialDao;
import com.example.demo.Dao.RoleDao;
import com.example.demo.Dao.StoresDao;
import com.example.demo.Dao.SupplierDao;
import com.example.demo.Dao.SuppliesDao;
import com.example.demo.Dao.SupplyOrderDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.validators.*;

@Controller
public class AdminController {
    @Autowired
    PlantDao plantDao;
    @Autowired
    EmployeeValidator employeeValidator;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    EmployeePhoneNoDao employeePhoneNoDao;
    @Autowired
    EmployeeEmailDao employeeEmailDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PlantValidator plantValidator;
    @Autowired
    ProductsDao productDao;
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RawMaterialDao rawMaterialDao;
    @Autowired
    MadeOfDao madeOfDao;
    @Autowired 
    JdbcTemplate jdbcTemplate;
    @Autowired
    StoresDao storesDao;
    @Autowired
    ManufacturesDao manufacturesDao;
    @Autowired
    SecurityService securityService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SuppliesDao suppliesDao;
    @Autowired
    SupplyOrderDao supplyOrderDao;
    
    @GetMapping("/detail")
    public String user_detail(Model m,Principal principal) throws NotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserName(principal.getName());
        userDao.load(conn, user);
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        System.out.println(x.size());
        Object y=x.get(0);
        role=(Role)y;
        System.out.println(role.getRoleName()+"detail");
        if(role.getRoleName().equals("Employee"))
            return "redirect:/employee/dashboard";
        else if(role.getRoleName().equals("Client"))
            return "redirect:/client/dashboard";
        else if(role.getRoleName().equals("Supplier"))
            return "redirect:/supplier/dashboard";
        else
            return "redirect:/admin/dashboard";
    }
    @Secured({"Admin"})
    @GetMapping("/admin/dashboard")
    public String admin_dashboard(Model m,Principal principal) throws NotFoundException, SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserName(principal.getName());
        userDao.load(conn, user);
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        Object y=x.get(0);
        role=(Role)y;
        System.out.println(role.getRoleName());
        if(!role.getRoleName().equals("Admin"))
            return "redirect:/invalid";
        System.out.println(role.getRoleName());
        m.addAttribute("username", user.getUserName());
        return "admin_dashboard";
    }
    

    

}