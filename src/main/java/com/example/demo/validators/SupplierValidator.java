package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.Dao.SupplierDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Supplier;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SupplierValidator implements Validator {
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private SupplierDao supplierDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return Supplier.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Supplier supplier = (Supplier) o;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty", new Object[]{"userName"}, "userName should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", new Object[]{"password"}, "password should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty", new Object[]{"confirmPassword"}, "confirmPassword should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty", new Object[]{"name"}, "name should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact", "NotEmpty", new Object[]{"contact"}, "contact should not be empty");

        
        if(!supplier.getPassword().equals(supplier.getConfirmPassword()))
        {
            errors.rejectValue("confirmPassword", "unequal", new Object[]{"confirmPassword"}, "Passwords do not match");
        }
        Supplier supplier1=new Supplier();
        supplier1.setContact(supplier.getContact());
        try {
            List l = supplierDao.searchMatching(conn, supplier1);
            if(!l.isEmpty())
            {
                errors.rejectValue("contact", "duplicate", new Object[]{"contact"}, "user with phone number already exist");
                
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        User user1=new User();
        user1.setUserName(supplier.getUserName());
        try {
            if (!userDao.searchMatching(conn, user1).isEmpty()) {
                errors.rejectValue("userName", "duplicate", new Object[]{"userName"}, "userName already exists");
            }
            System.out.println("errrrrrrr");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (supplier.getPassword().length() < 8 || supplier.getPassword().length() > 32) {
            errors.rejectValue("password", "password", new Object[]{"password"}, "Password does not meet policy requirements");

        }


    }
}