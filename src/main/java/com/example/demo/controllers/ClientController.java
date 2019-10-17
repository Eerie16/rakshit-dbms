package com.example.demo.controllers;

import com.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.eclipse.jdt.internal.compiler.ast.Clinit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.sql.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Client;
import com.example.demo.models.ClientEmail;
import com.example.demo.models.ClientOrder;
import com.example.demo.models.ConsistsOf;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeEmail;
import com.example.demo.models.EmployeePhoneNo;
import com.example.demo.models.Plant;
import com.example.demo.models.RawMaterial;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.example.NotFoundException;
import com.example.demo.Dao.ClientDao;
import com.example.demo.Dao.ClientEmailDao;
import com.example.demo.Dao.ClientOrderDao;
import com.example.demo.Dao.ConsistsOfDao;
import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.EmployeeEmailDao;
import com.example.demo.Dao.EmployeePhoneNoDao;
import com.example.demo.Dao.PlantDao;
import com.example.demo.Dao.ProductsDao;
import com.example.demo.Dao.RawMaterialDao;
import com.example.demo.Dao.RoleDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.validators.*;

@Controller
public class ClientController {
   
    @Autowired
    UserDao userDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    ClientEmailDao clientEmailDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired 
    ProductsDao productDao;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ClientOrderDao clientOrderDao;
    @Autowired
    ConsistsOfDao consistsOfDao;
    @Autowired
    SecurityService securityService;
    @Autowired
    ClientValidator clientValidator;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailValidator emailValidator;
    @Autowired
@Qualifier("sessionRegistry")
private SessionRegistry sessionRegistry;
    @GetMapping("/client/dashboard")
    public String client_dashboard(Model m,Principal principal) throws NotFoundException, SQLException
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
        // if(!(role.getRoleName().equals("Client")||role.getRoleName().equals("Admin")))
        //     return "redirect:/invalid";
        m.addAttribute("username", principal.getName());
        return "client_dashboard";
    }
    @GetMapping("/client/new")
    public String client_new(Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.addAttribute("client", new Client());
        conn.close();
        return "client_new";
    }
    @PostMapping("/client/new")
    public String client_add( @ModelAttribute("client") Client client, BindingResult result, Model m)
            throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clientValidator.validate(client, result);
        if(result.hasErrors()){
            return "client_new";
        }

        // employeeValidator.validate(employee, result);
        // if (result.hasErrors()) {
        //     m.addAttribute("plants", plantDao.loadAll(conn));
        //     return "employee_new";
        // }
        clientValidator.validate(client, result);
        if(result.hasErrors())
        {
            m.addAttribute("client", new Client());
            return "client_new";
        }
        User user = new User();
        user.setUserName(client.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        Role role = new Role();
        role.setRoleName("Client");
        userDao.create(conn, user);
        int id = user.getUserId();
        client.setClientId(id);
        clientDao.create(conn, client);
        role.setUserId(id);
        roleDao.create(conn, role);

        securityService.autoLogin(user.getUserName(), user.getPassword());

        return "redirect:/client/dashboard" ;
    }
    @GetMapping("/client/detail")
    public String employee_detail (Model m,Principal principal) throws SQLException, NotFoundException {
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
        if(!(role.getRoleName().equals("Client")||role.getRoleName().equals("Admin")))
            return "redirect:/invalid";
        int clientId=user.getUserId();
        System.out.println("\n\n"+user.getUserName()+"\n\n");
        Client client =clientDao.getObject(conn, clientId);
        ClientEmail clientEmail = new ClientEmail();
        clientEmail.setClientId(clientId);
        m.addAttribute("rolename", role.getRoleName());
        m.addAttribute("client", client);
        m.addAttribute("clientEmails", clientEmailDao.searchMatching(conn, clientEmail));
        m.addAttribute("employees", employeeDao.loadAll(conn));
        conn.close();
        return "client_detail";
    }

    @PostMapping("/check/user")
    public ResponseEntity<Integer> check_user(@RequestParam Integer userId)
            throws NotFoundException, SQLException
    {   
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }

       User user =userDao.getObject(conn, userId);
       System.out.println(user.getUserName());
       List<Object> principals = sessionRegistry.getAllPrincipals();
       for (Object principal: principals) {
           if (principal instanceof User) {
                System.out.println(((User) principal).getUserName());
           }
       }
        return  new ResponseEntity<>(HttpStatus.OK);    
    }
    @PostMapping("/client/email/add")
    public ResponseEntity<Integer> employee_add_contact(@RequestParam Integer clientId, @RequestParam String email,
            Model m,Principal principal)throws SQLException, NotFoundException {
                System.out.println("\n\n"+email+"\n\n");
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
        if(!(role.getRoleName().equals("Client")||role.getRoleName().equals("Admin")))
            return new ResponseEntity<>(HttpStatus.OK);
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(!email.matches(regex))
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        ClientEmail clientEmail=new ClientEmail();
        clientEmail.setAll(clientId, email);
        clientEmailDao.create(conn, clientEmail);
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/client/edit")
    public String client_update(@ModelAttribute Client client, Model m,Principal principal)
            throws NoSuchElementException, SQLException, NotFoundException {
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
        if(!(role.getRoleName().equals("Client")||role.getRoleName().equals("Admin")))
            return "redirect:/invalid";
        client.setClientId(user.getUserId());
        Client old = new Client();
        old.setClientId(client.getClientId());
        clientDao.load(conn, old);

        if (client.getCity() == null) {
            client.setCity(old.getCity());
        }

        if (client.getStreet() == null) {
            client.setStreet(old.getStreet());
        }

        if (client.getPhoneNumber() == 0) {
            client.setPhoneNumber(old.getPhoneNumber());
        }

        if (client.getGender() == null) {
            client.setGender(old.getGender());
        }

        if (client.getLname() == null) {
            client.setLname(old.getLname());
        }

        if (client.getFname() == null) {
            client.setFname(old.getLname());
        }

        if (client.getEmployeeId() == 0) {
            client.setEmployeeId(old.getEmployeeId());
        }

        if (client.getOrganisation() == null) {
            client.setOrganisation(old.getOrganisation());
        }
        clientDao.save(conn, client);
        conn.close();
        return "redirect:/client/detail";
    }
    @GetMapping("/client/{id}/previous/order")
    public String show_order(@PathVariable("id") Integer clientId,Model m,Principal principal) throws NotFoundException, SQLException
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
        int userId=user.getUserId();
        if(userId!=clientId&&(role.getRoleName().equals("Client")||role.getRoleName().equals("Supplier")))
            return "redirect:/invalid";
        Client client =clientDao.getObject(conn, clientId);  
        ClientOrder clientOrder=new ClientOrder();
        clientOrder.setClientId(client.getClientId());
        m.addAttribute("orders", clientOrderDao.searchMatching(conn, clientOrder));
        return "show_orders";
    }
    


}