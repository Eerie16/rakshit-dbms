package com.example.demo.controllers;

import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.example.NotFoundException;
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
import com.example.demo.models.Role;
import com.example.demo.models.Supplier;
import com.example.demo.models.Supplies;
import com.example.demo.models.SupplyOrder;
import com.example.demo.models.User;
import com.example.demo.service.SecurityService;
import com.example.demo.validators.EmployeeValidator;
import com.example.demo.validators.PlantValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupplyController {
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
    @GetMapping("/supplier/new")
    public String supplier_new(Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.addAttribute("supplier", new Supplier());
        conn.close();
        return "supplier_new";
    }
    @GetMapping("/supplier/dashboard")
    public String supplier_dashboard(Model m,Principal principal) throws NotFoundException, SQLException
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
        Supplier supplier =new Supplier();
        supplier.setSupplierId(user.getUserId());
        supplierDao.load(conn, supplier);    
        m.addAttribute("name",supplier.getName());
        m.addAttribute("supplier", supplier);
        return "supplier_dashboard";
        
    }
    @PostMapping("/supplier/new")
    public String supplier_add(@ModelAttribute("supplier") Supplier supplier,BindingResult result, Model m)
            throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserName(supplier.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(supplier.getPassword()));
        Role role = new Role();
        role.setRoleName("Supplier");
        userDao.create(conn, user);
        int id = user.getUserId();
        supplier.setSupplierId(id);
        System.out.println(id);
        role.setUserId(id);
        roleDao.create(conn, role);
        supplierDao.create(conn, supplier);
        
        securityService.autoLogin(user.getUserName(), user.getPassword());
        return "redirect:/supplier/dashboard";
    }
    @GetMapping("/supplier/detail/{id}")
    public String supplier_detail(@PathVariable("id") Integer supplierId,Model m) throws NotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserId(31);;
        userDao.load(conn, user);
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        Object y=x.get(0);
        role=(Role)y;
        m.addAttribute("userid", user.getUserId());
        Supplier supplier = supplierDao.getObject(conn, supplierId);
        m.addAttribute("supplier", supplier);
        m.addAttribute("rawMaterials",rawMaterialDao.loadAll(conn));
        
        m.addAttribute("suppliedRawMaterials",jdbcTemplate.queryForList("select r.rawMaterialId, r.name from Supplies s,RawMaterial r where s.rawMaterialId=r.rawMaterialId and s.supplierId=?",supplierId) );
        m.addAttribute("PendingOrders",jdbcTemplate.queryForList("select s.supplyOrderId,r.name,e.Lname,e.Fname,s.quantity from SupplyOrder s,RawMaterial r,Employee e where s.rawMaterialId=r.rawMaterialId and s.employeeId=e.employeeId and supplierId=? and status like ?", supplierId,"pending"));
        m.addAttribute("CompletedOrders",jdbcTemplate.queryForList("select r.name,e.Lname,e.Fname,s.quantity from SupplyOrder s,RawMaterial r,Employee e where s.rawMaterialId=r.rawMaterialId and s.employeeId=e.employeeId and supplierId=? and status like ?", supplierId,"completed"));
        return "supplier_detail";
        ////post mapping, with raw,and supporder
    }
    @GetMapping("/supplier/show")
    public String supplier_show(Model m) throws NotFoundException, SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserId(31);;
        userDao.load(conn, user);
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        Object y=x.get(0);
        // role=(Role)y;
        // if(role.getRoleName().equals("Supplier")||role.getRoleName().equals("Client"))
        // return "redirect:/invalid";
        m.addAttribute("suppliers", supplierDao.loadAll(conn));
        return "show_supplier";
    }

    @GetMapping("/supplyOrder/edit/{id}")
    public String supplyOrderEdit(@PathVariable("id") Integer supplyOrderId,Model m,Principal principal) throws NotFoundException, SQLException
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
        role=(Role)x.get(0);
        if(!role.getRoleName().equals("Admin")&&!role.getRoleName().equals("Employee"))
        return "redirect:/invalid";
        SupplyOrder supplyOrder=supplyOrderDao.getObject(conn, supplyOrderId);
        supplyOrder.setStatus("completed");
        supplyOrderDao.save(conn, supplyOrder);
        return "redirect:/supplier/detail/"+supplyOrder.getSupplierId();
    }
    @PostMapping("/supplyOrder/add")
    public ResponseEntity<Integer> add_supplierOrder(@RequestParam Integer employeeId,@RequestParam Integer supplierId,@RequestParam Integer rawMaterialId,@RequestParam Integer quantity,Model m) throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SupplyOrder supplyOrder=new SupplyOrder();
        supplyOrder.setEmployeeId(employeeId);
        supplyOrder.setQuantity(quantity);
        supplyOrder.setRawMaterialId(rawMaterialId);
        supplyOrder.setSupplierId(supplierId);
        supplyOrder.setStatus("pending");
        supplyOrderDao.create(conn, supplyOrder);
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/supplier/rawMaterial/add")
    public ResponseEntity<Integer> supplier_add_rawMaterial(@RequestParam Integer supplierId, @RequestParam Integer rawMaterialId,
            Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Supplies supplies=new Supplies();
        supplies.setAll(rawMaterialId, supplierId);
        if(suppliesDao.searchMatching(conn, supplies).isEmpty())
            suppliesDao.create(conn, supplies);
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}