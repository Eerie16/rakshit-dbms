package com.example.demo.controllers;

import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

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
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeEmail;
import com.example.demo.models.EmployeePhoneNo;
import com.example.demo.models.Role;
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
public class EmployeeController {
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
    @GetMapping("/employee/new")
    public String employee_new(Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.addAttribute("employee", new Employee());
        m.addAttribute("plants", plantDao.loadAll(conn));
        conn.close();
        return "employee_new";
    }

    @PostMapping("/employee/new")
    public String employee_add(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model m)
            throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employeeValidator.validate(employee, result);
        if (result.hasErrors()) {
            m.addAttribute("plants", plantDao.loadAll(conn));
            return "employee_new";
        }
        User user = new User();
        user.setUserName(employee.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        
        Role role = new Role();
        role.setRoleName("Employee");
        userDao.create(conn, user);
        int id = user.getUserId();
        employee.setEmployeeId(id);
        employee.setSalary(1);
        employee.setPosition("default Position");
        employeeDao.create(conn, employee);
        role.setUserId(id);
        roleDao.create(conn, role);
        m.addAttribute("plants", plantDao.loadAll(conn));

        securityService.autoLogin(user.getUserName(), user.getPasswordConfirm());

        return "redirect:/employee/detail/"+user.getUserId();
    }
    @GetMapping("/employee/dashboard")
    public String employee_dashboard(Model m,Principal principal) throws NotFoundException, SQLException
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
        System.out.println("/n/n"+user.getUserName()+"/n/n");
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        Object y=x.get(0);
        role=(Role)y;
        Employee employee=new Employee();
        employee.setEmployeeId(user.getUserId());
        employeeDao.load(conn, employee);
        m.addAttribute("username", user.getUserName());
        m.addAttribute("employee", employee);
        return "employee_dashboard";
    }
    @GetMapping("/employee/detail/{id}")
    public String employee_detail(@PathVariable("id") Integer employeeId,Model m, Principal principal) throws SQLException, NotFoundException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserName(principal.getName());
        userDao.load(conn, user);
        System.out.println("/n/n"+user.getUserName()+"/n/n");
        Role role=new Role();
        role.setUserId(user.getUserId());
        List x = roleDao.searchMatching(conn, role);
        Object y=x.get(0);
        role=(Role)y;
        if(user.getUserId()!=employeeId&&!role.getRoleName().equals("Admin"))
        return "redirect:/inValidUrl";
        System.out.println("/n/n"+role.getRoleName()+"/n/n");
        Employee employee = employeeDao.getObject(conn, employeeId);
        EmployeePhoneNo employeePhoneNo = new EmployeePhoneNo();
        EmployeeEmail employeeEmail = new EmployeeEmail();
        employeePhoneNo.setEmployeeId(employeeId);
        employeeEmail.setEmployeeId(employeeId);
        m.addAttribute("rolename", role.getRoleName());
        m.addAttribute("employee", employee);
        m.addAttribute("plants", plantDao.loadAll(conn));
        m.addAttribute("employeePhoneNos", employeePhoneNoDao.searchMatching(conn, employeePhoneNo));
        m.addAttribute("emails", employeeEmailDao.searchMatching(conn, employeeEmail));
        conn.close();
        return "employee_detail";
    }

    @PostMapping("/employee/contacts/add")
    public ResponseEntity<Integer> employee_add_contact(@RequestParam Integer employeeId, @RequestParam Integer phoneNo,
            Model m) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EmployeePhoneNo employeePhoneNo = new EmployeePhoneNo();
        employeePhoneNo.setAll(employeeId, phoneNo);
        employeePhoneNoDao.create(conn, employeePhoneNo);
        conn.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/employee/edit")
    public String employee_update(@ModelAttribute Employee employee, Model m,Principal principal)
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
        employee.setEmployeeId(user.getUserId());
        Employee old = new Employee();
        old.setEmployeeId(employee.getEmployeeId());
        employeeDao.load(conn, old);

        if (employee.getPosition() == null) {
            employee.setPosition(old.getPosition());
        }

        if (employee.getSalary() == 0) {
            employee.setSalary(old.getSalary());
        }

        if (employee.getCity() == null) {
            employee.setCity(old.getCity());
        }

        if (employee.getDateOfJoining() == null) {
            employee.setDateOfJoining(old.getDateOfJoining());
        }

        if (employee.getGender() == null) {
            employee.setGender(old.getGender());
        }

        if (employee.getLname() == null) {
            employee.setLname(old.getLname());
        }

        if (employee.getFname() == null) {
            employee.setFname(old.getFname());
        }

        if (employee.getPlantId() == 0) {
            employee.setPlantId(old.getPlantId());
        }
        employeeDao.save(conn, employee);
        conn.close();
        return "redirect:/employee/" + employee.getEmployeeId();
    }


}