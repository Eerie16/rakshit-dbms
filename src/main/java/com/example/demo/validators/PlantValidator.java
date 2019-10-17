package com.example.demo.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Dao.PlantDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.models.Employee;
import com.example.demo.models.Plant;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PlantValidator implements Validator {
    
    @Autowired
    PlantDao plantDao;
    @Override
    public boolean supports(Class<?> aClass) {
        return Plant.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Plant plant = (Plant) o;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/draft1", "root", "rakshit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentCapacity", "NotEmpty");
        
        if(plant.getAddress()==null)
        {
            errors.rejectValue("address", "empty", "address cannot be empty");

        }
        if(plant.getName()==null)
        {
            errors.rejectValue("name", "empty", "name cannot be empty");
            
        }if(plant.getCurrentCapacity()==0)
        {
            errors.rejectValue("currentCapacity", "empty", "currentCapacity cannot be zero");
            
        }
    }
}