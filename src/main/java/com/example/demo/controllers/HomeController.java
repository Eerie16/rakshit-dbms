package com.example.demo.controllers;

import com.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.constraints.Null;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Client;
import com.example.demo.models.ClientOrder;
import com.example.demo.models.Employee;
import com.example.demo.models.Plant;
import com.example.demo.models.RawMaterial;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.example.NotFoundException;
import com.example.demo.Dao.ClientDao;
import com.example.demo.Dao.ClientOrderDao;
import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.PlantDao;
import com.example.demo.Dao.RawMaterialDao;

@Controller
public class HomeController {
    @Autowired
    JdbcTemplate template;
    @Autowired
    ClientDao clientDao;
    @Autowired
    ClientOrderDao clientOrderDao;
    @Autowired
    EmployeeDao employeeDao;
    // @GetMapping("/client/new")
    // public String newClient(Model m){
    //     m.addAttribute("client",new Client());
    //     return "new_client";
    // }
    // @GetMapping("/index")
    // public String index(Model m) throws SQLException, NotFoundException {

    //     Connection conn = null;
    //     try {
    //         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     for (Field x : ClientOrder.class.getDeclaredFields()) {
    //         System.out.println(x.getName() + "\n");
    //     }

    //     return "index";
    // }

    // @GetMapping("/Client/{id}")
    // public String clientDetail(Model m, @PathVariable("id") int clientId) throws NotFoundException, SQLException {
    //     Connection conn = null;
    //     try {
    //         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     Client client = new Client();
    //     client.setClientId(clientId);
    //     clientDao.load(conn, client);
    //     ClientOrder clientOrder = new ClientOrder();
    //     List<ClientOrder> list = clientOrderDao.loadAll(conn);

    //     // List<List<Object>> list1 = new ArrayList<>();
    //     // List<Object> attributeList=new ArrayList<>();
    //     // for(Method i:list.get(0).getClass().getDeclaredMethods())
    //     // if(i.getName().charAt(0) == 'g')
    //     // attributeList.add(i.getName().substring(3));
    //     // list1.add(attributeList);
    //     // for (ClientOrder i : list) {
    //     //     List<Object> y = new ArrayList<>();
    //     //     for (Method x : i.getClass().getDeclaredMethods()) {
    //     //         if (x.getName().charAt(0) == 'g')
    //     //             try {
    //     //                 y.add(x.invoke(i, new Object[] {}));
    //     //             } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        
    //     //                 e.printStackTrace();
    //     //             }
    //     //     }
    //     //     list1.add(y);
    //     // }
    //     m.addAttribute("list", list);
    //     return "clientDetail";
    // }

}