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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.example.demo.models.ClientEmail;
import com.example.demo.models.ClientOrder;
import com.example.demo.models.ConsistsOf;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeEmail;
import com.example.demo.models.EmployeePhoneNo;
import com.example.demo.models.Feedback;
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
import com.example.demo.Dao.FeedbackDao;
import com.example.demo.Dao.PlantDao;
import com.example.demo.Dao.ProductsDao;
import com.example.demo.Dao.RawMaterialDao;
import com.example.demo.Dao.RoleDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.validators.*;

@Controller
public class ClientOrderController {
   
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
    FeedbackDao feedbackDao;
    @GetMapping("/client/order/new")
    public String place_order(Model m,Principal principal) throws NotFoundException, SQLException
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

        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String sql="insert into ClientOrder(receivedDate,status,clientId) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
    	jdbcTemplate.update(
    	    new PreparedStatementCreator() {
    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    	            PreparedStatement pst =
                        con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                        pst.setDate(1, date);
                        pst.setString(2, "in consideration");
                        pst.setInt(3, user.getUserId());
    	            return pst;
    	        }
    	    },
            keyHolder);
        
        int clientOrderId=keyHolder.getKey().intValue();
        return "redirect:/client/order/"+clientOrderId;
    }
    @GetMapping("/client/order/{clientOrderId}")
    public String edit_order(@PathVariable("clientOrderId") Integer clientOrderId,Model m,Principal principal) throws NotFoundException, SQLException
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
        List x=roleDao.searchMatching(conn, role);
        role= (Role)x.get(0);
        ClientOrder clientOrder=new ClientOrder();
        clientOrder.setClientOrderId(clientOrderId);
        clientOrderDao.load(conn, clientOrder);
        // if(clientOrder.getClientId()!=user.getUserId()&&(role.getRoleName().equals("Client")||role.getRoleName().equals("Supplier")))
        //     return "redirect:/invalid";
        m.addAttribute("rolename", role.getRoleName());
        Client client=new Client();
        client.setClientId(clientOrder.getClientOrderId());
        clientOrderDao.load(conn, clientOrder);
        m.addAttribute("clientOrder", clientOrder);
        m.addAttribute("list",jdbcTemplate.queryForList("select c.quantity,p.name from ConsistsOf c,Products p where c.productId=p.productId and c.clientOrderId=?",clientOrder.getClientOrderId()));
        m.addAttribute("products", productDao.loadAll(conn));
        m.addAttribute("remark", clientOrder.getRemark());
        Feedback feedback=new Feedback();
        feedback.setClientOrderId(clientOrderId);
        m.addAttribute("feedbacks", feedbackDao.searchMatching(conn, feedback));
        return "edit_order";
    }
    @PostMapping("/clientOrder/add")
    public ResponseEntity<Integer> add_order_product(@RequestParam Integer clientOrderId,@RequestParam Integer productId,@RequestParam Integer quantity,Model m,Principal principal) throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ClientOrder clientOrder=new ClientOrder();
        clientOrder.setClientOrderId(clientOrderId);
        try {
            clientOrderDao.load(conn, clientOrder);
        } catch (NotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        System.out.println("\n\n"+quantity+"\n\n");
        ConsistsOf consistsOf=new ConsistsOf();
        consistsOf.setAll(clientOrderId, productId, quantity);

        try {
            consistsOfDao.load(conn, consistsOf);
            quantity+=consistsOf.getQuantity();
            consistsOf.setQuantity(quantity);
            consistsOfDao.save(conn, consistsOf);
        } catch (NotFoundException e) {
            consistsOfDao.create(conn,consistsOf);
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/client/order/remark")
    public ResponseEntity<Integer> add_remark(@RequestParam Integer clientOrderId,@RequestParam String remark,Model m)
            throws  SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ClientOrder clientOrder=new ClientOrder();
        clientOrder.setClientOrderId(clientOrderId);
        try {
			clientOrderDao.load(conn, clientOrder);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
		}
        clientOrder.setRemark(remark);
        try {
            clientOrderDao.save(conn, clientOrder);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/client/previous/order")
    public String show_order(Model m,Principal principal) throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserName(principal.getName());
        try {
            userDao.load(conn, user);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x=roleDao.searchMatching(conn, role);
        role= (Role)x.get(0);
        if(role.getRoleName().equals("client")||role.getRoleName().equals("Supplier"))
            return "redirect:/invalid";
        m.addAttribute("rolename", role.getRoleName());
        m.addAttribute("user", user);
        m.addAttribute("orders", clientOrderDao.loadAll(conn));
        return "show_orders";
    }
}