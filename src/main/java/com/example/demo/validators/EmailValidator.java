package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Client;
import com.example.demo.models.Employee;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EmailValidator implements Validator {
    
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
        String email=(String) o;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";  
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eamil", "NotEmpty");
        if (!email.matches(regex)) {
            errors.rejectValue("email", "invalid email");
        }

    }
}