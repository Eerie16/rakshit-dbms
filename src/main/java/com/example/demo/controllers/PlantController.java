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
public class PlantController {
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
@GetMapping("/plant/show")
    public String plant_show(Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.addAttribute("plants", plantDao.loadAll(conn));
        return "plant_show";
    }

    @PostMapping("/plant/new")
    public ResponseEntity<Integer> plant_new(@RequestParam String address, @RequestParam String name,
            @RequestParam Integer currentCapacity, Model m) throws SQLException {
        Plant plant = new Plant();
        plant.setAddress(address);
        plant.setCurrentCapacity(currentCapacity);
        plant.setName(name);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        plantDao.create(conn, plant);
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/plant/{id}/edit")
    public String plant_edit(@PathVariable("id") Integer plantId, Model m) throws SQLException, NotFoundException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plantDao.load(conn, plant);
        m.addAttribute("plant", plant);
        List<Map<String, Object>> x = jdbcTemplate.queryForList(
                "select r.rawMaterialId,r.name,m.quantity from Stores m,RawMaterial r where m.rawMaterialId=r.rawMaterialId and (m.plantId=?)",
                plantId);
        m.addAttribute("rawMaterialsStored", x);
        m.addAttribute("rawMaterials", rawMaterialDao.loadAll(conn));
        conn.close();
        return "plant_edit";
    }

    @PostMapping("/plant/{id}/edit")
    public String plant_edit(@PathVariable("id") Integer plantId, @ModelAttribute Plant plant, Model m)
            throws NotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Plant plant1 = new Plant();
        plant1.setPlantId(plantId);
        plantDao.load(conn, plant1);
        if (plant.getAddress() != null)
            plant1.setAddress(plant.getAddress());
        if (plant.getName() != null)
            plant1.setName(plant.getName());
        if (plant.getCurrentCapacity() != 0)
            plant1.setCurrentCapacity(plant.getCurrentCapacity());
        plantDao.save(conn, plant1);
        conn.close();
        return "redirect:/plant/show";
    }
    @PostMapping("/plant/{id}/rawMaterial/add")
    public ResponseEntity<Integer> plant_add_rawMaterial(@PathVariable("id") Integer plantId,@RequestParam Integer rawMaterialId, @RequestParam Integer quantity,
            Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stores stores=new Stores();
        stores.setAll(rawMaterialId, plantId, quantity);
        try {
            storesDao.load(conn, stores);
            stores.setQuantity(quantity);
            storesDao.save(conn, stores);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            storesDao.create(conn, stores);
        }
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}