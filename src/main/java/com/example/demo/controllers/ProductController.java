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
public class ProductController {
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
    @GetMapping("/product/show")
    public String product_show(Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.addAttribute("products", productDao.loadAll(conn));
        m.addAttribute("employees", employeeDao.loadAll(conn));
        m.addAttribute("newProduct", new Products());
        conn.close();
        return "product_show";
    }
    @PostMapping("/product/new")
    public String product_new(@ModelAttribute("product") Products product,BindingResult result,Model m)
            throws SQLException {
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        productDao.create(conn, product);
        m.addAttribute("products", productDao.loadAll(conn));
        conn.close();
        return "redirect:/product/show";
    }
    @GetMapping("/product/{id}/edit")
    public String product_edit(@PathVariable("id") Integer productId,Model m) throws SQLException, NotFoundException
    {
        Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        Products product = new Products();
        product.setProductId(productId);
        productDao.load(conn, product);
        m.addAttribute("product", product);
        m.addAttribute("employees", employeeDao.loadAll(conn));
        List<Map<String, Object>> stmt=jdbcTemplate.queryForList("select r.name,m.quantity from MadeOf m,RawMaterial r where m.rawMaterialId=r.rawMaterialId and (m.productId=?)",productId);
        m.addAttribute("rawMaterialsRequired", stmt);
        m.addAttribute("rawMaterials", rawMaterialDao.loadAll(conn));
        m.addAttribute("plantsManufactures", jdbcTemplate.queryForList("select p.name,m.timeRequired from Plant p,Manufactures m where p.plantId=m.plantId and m.productId=?",productId));
        m.addAttribute("plants", plantDao.loadAll(conn));
        conn.close();
        return "product_edit";
    }
    @PostMapping("/product/{id}/rawMaterial/add")
    public ResponseEntity<Integer> product_add_rawMaterial(@PathVariable("id") Integer productId,@RequestParam Integer rawMaterialId, @RequestParam Integer quantity,
            Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MadeOf madeOf=new MadeOf();
        madeOf.setAll(productId, rawMaterialId, quantity);
        try {
            madeOfDao.load(conn, madeOf);
            System.out.println("\n\n"+madeOf.getQuantity()+"\n\n");
            madeOf.setQuantity(quantity);
            madeOfDao.save(conn,madeOf);
        } catch (NotFoundException e) {
            madeOfDao.create(conn, madeOf); 
        }
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/product/{id}/plant/add")
    public ResponseEntity<Integer> product_add_plant(@PathVariable("id") Integer productId,@RequestParam Integer plantId, @RequestParam Integer hours,
            Model m) throws SQLException {
                Time time=new Time(hours, 0, 0);
                System.out.println("\n\n"+hours+"\n\n");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Manufactures manufactures=new Manufactures();
        manufactures.setAll(productId, plantId, time);
        try {
            manufacturesDao.load(conn, manufactures);
            manufactures.setTimeRequired(time);
            manufacturesDao.save(conn, manufactures);
        } catch (NotFoundException e) {
            manufacturesDao.create(conn, manufactures);
        }

        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/product/{id}/edit")
    public String product_edit(@PathVariable("id") Integer productId,@ModelAttribute Products product,Model m)
            throws NotFoundException, SQLException
    {
        Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        Products product1=new Products();
        product1.setProductId(productId);
        productDao.load(conn, product1);
        if(product.getPrice()!=0)
        product1.setPrice(product.getPrice());
        if(product.getName()!=null)
        product1.setName(product.getName());
        if(product.getEmployeeId()!=0)
        product1.setEmployeeId(product.getEmployeeId());
        productDao.save(conn, product1);
        conn.close();
        return "redirect:/product/show";
    }
}