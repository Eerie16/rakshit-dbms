package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.Dao.ClientDao;
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
public class ClientValidator implements Validator {
    
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty");
        if(!client.getPassword().equals(client.getConfirmPassword()))
        {
            errors.rejectValue("confirmPassword","unequal", "Passwords do not match");
        }
        Client client1=new Client();
        client1.setPhoneNumber(client.getPhoneNumber());
        try {
            List l = clientDao.searchMatching(conn, client1);

            if(!l.isEmpty())
            {
                errors.rejectValue("phoneNumber", "duplicate", "user with phone number already exist");
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        User user1=new User();
        user1.setUserName(client.getUserName());
        try {
            if (!userDao.searchMatching(conn, user1).isEmpty()) {
                errors.rejectValue("userName", "duplicate", "userName already exists");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (client.getPassword().length() < 8 || client.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        // if (customer.getContact().length() != 10) {
        //     errors.rejectValue("contact", "length", new Object[]{"contact"}, "Contact must be number of 10 digits");        
        // }

        // if (clientDao.isCustomerWithContactExists(customer.getContact())) {
        //     errors.rejectValue("contact", "duplicate", new Object[]{"contact"}, "Customer with contact already exists");
        // }

        if( !(client.getGender().equals("M") || client.getGender().equals("F")) ){
            errors.rejectValue("gender", "type", new Object[]{"gender"}, "Gender type should be M or F");
        }

    }
}