package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.Dao.ClientDao;
import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.ProductsDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Client;
import com.example.demo.models.Employee;
import com.example.demo.models.Products;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {
    
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ProductsDao productDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return Products.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Products products = (Products) o;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty", new Object[]{"name"}, "name should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty", new Object[]{"price"}, "price should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId", "NotEmpty", new Object[]{"employeeId"}, "employeeId should not be empty");

        if (products.getPrice() < 0) {
            errors.rejectValue("price", "negative", new Object[]{"price"}, "Price cannot be negative");
        }


    }
}