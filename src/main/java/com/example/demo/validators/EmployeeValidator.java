package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Employee;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {
    
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private UserDao userDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty", new Object[]{"userName"}, "userName should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", new Object[]{"password"}, "password should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty", new Object[]{"confirmPassword"}, "confirmPassword should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname", "NotEmpty", new Object[]{"lname"}, "lname should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "NotEmpty", new Object[]{"fname"}, "fname should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty", new Object[]{"gender"}, "gender should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "NotEmpty", new Object[]{"salary"}, "salary should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty", new Object[]{"city"}, "city should not be empty");
        
        // if(!employee.getPassword().equals(employee.getConfirmPassword()))
        // {
        //     errors.rejectValue("confirmPassword","unequal", "Passwords do not match");
        // }
        User user1=new User();
        user1.setUserName(employee.getUserName());
        try {
            if (!userDao.searchMatching(conn, user1).isEmpty()) {
                errors.rejectValue("userName", "duplicate", new Object[]{"userName"}, "userName already exists");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(!employee.getPassword().equals(employee.getConfirmPassword()))
        {
            errors.rejectValue("confirmPassword", "unequal", new Object[]{"confirmPassword"}, "asswords do not match");
        }
        if (employee.getPassword().length() < 8 || employee.getPassword().length() > 32) {
            errors.rejectValue("password", "password", new Object[]{"password"}, "Password does not meet policy requirements");

        }

    }
}