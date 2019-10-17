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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
        
        // if(!employee.getPassword().equals(employee.getConfirmPassword()))
        // {
        //     errors.rejectValue("confirmPassword","unequal", "Passwords do not match");
        // }
        // User user1=new User();
        // user1.setUserName(employee.getUserName());
        // try {
        //     if (!userDao.searchMatching(conn, user1).isEmpty()) {
        //         errors.rejectValue("userName", "duplicate", "userName already exists");
        //     }
        // } catch (SQLException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // if (customer.getContact().length() != 10) {
        //     errors.rejectValue("contact", "length", new Object[]{"contact"}, "Contact must be number of 10 digits");        
        // }

        // if (employeeDao.isCustomerWithContactExists(customer.getContact())) {
        //     errors.rejectValue("contact", "duplicate", new Object[]{"contact"}, "Customer with contact already exists");
        // }

        // if( !(employee.getGender().equals("M") || employee.getGender().equals("F")) ){
        //     errors.rejectValue("gender", "type", new Object[]{"gender"}, "Gender type should be M or F");
        // }

        // if(!(employee.getSalary()>0)){
        //     errors.rejectValue("salary", "value",new Object[]{"salary"},"Salary should be positive integer");
        // }

    }
}