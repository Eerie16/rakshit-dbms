package com.example.demo.models;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class User implements Cloneable, Serializable {

      /** 
       * Persistent Instance variables. This data is directly 
       * mapped to the columns of database table.
       */
      private int userId;
      private String userName;
      private String password;
      private String passwordConfirm;
  
      /** 
       * Constructors. DaoGen generates two constructors by default.
       * The first one takes no arguments and provides the most simple
       * way to create object instance. The another one takes one
       * argument, which is the primary key of the corresponding table.
       */
  
      public User () {
  
      }
  
      public User (int userIdIn) {
  
            this.userId = userIdIn;
  
      }
  
  
      /** 
       * Get- and Set-methods for persistent variables. The default
       * behaviour does not make any checks against malformed data,
       * so these might require some manual additions.
       */
  
      public int getUserId() {
            return this.userId;
      }
      public void setUserId(int userIdIn) {
            this.userId = userIdIn;
      }
  
      public String getUserName() {
            return this.userName;
      }
      public void setUserName(String userNameIn) {
            this.userName = userNameIn;
      }
  
      public String getPassword() {
            return this.password;
      }
      public void setPassword(String passwordIn) {
            this.password = passwordIn;
      }
      public String getPasswordConfirm() {
            return this.passwordConfirm;
      }
      public void setPasswordConfirm(String passwordConfirm) {
            this.password = passwordConfirm;
      }
  
  
      /** 
       * setAll allows to set all persistent variables in one method call.
       * This is useful, when all data is available and it is needed to 
       * set the initial state of this object. Note that this method will
       * directly modify instance variales, without going trough the 
       * individual set-methods.
       */
  
      public void setAll(int userIdIn,
            String userNameIn,
            String passwordIn) {
            this.userId = userIdIn;
            this.userName = userNameIn;
            this.password = passwordIn;
      }
  
  
      /** 
       * hasEqualMapping-method will compare two User instances
       * and return true if they contain same values in all persistent instance 
       * variables. If hasEqualMapping returns true, it does not mean the objects
       * are the same instance. However it does mean that in that moment, they 
       * are mapped to the same row in database.
       */
      public boolean hasEqualMapping(User valueObject) {
  
            if (valueObject.getUserId() != this.userId) {
                      return(false);
            }
            if (this.userName == null) {
                      if (valueObject.getUserName() != null)
                             return(false);
            } else if (!this.userName.equals(valueObject.getUserName())) {
                      return(false);
            }
            if (this.password == null) {
                      if (valueObject.getPassword() != null)
                             return(false);
            } else if (!this.password.equals(valueObject.getPassword())) {
                      return(false);
            }
  
            return true;
      }
  
  
  
      /**
       * toString will return String object representing the state of this 
       * valueObject. This is useful during application development, and 
       * possibly when application is writing object states in textlog.
       */
      public String toString() {
          StringBuffer out = new StringBuffer(this.getDaogenVersion());
          out.append("\nclass User, mapping to table User\n");
          out.append("Persistent attributes: \n"); 
          out.append("userId = " + this.userId + "\n"); 
          out.append("userName = " + this.userName + "\n"); 
          out.append("password = " + this.password + "\n"); 
          return out.toString();
      }
  
  
      /**
       * Clone will return identical deep copy of this valueObject.
       * Note, that this method is different than the clone() which
       * is defined in java.lang.Object. Here, the retuned cloned object
       * will also have all its attributes cloned.
       */
      public Object clone() {
          User cloned = new User();
  
          cloned.setUserId(this.userId); 
          if (this.userName != null)
               cloned.setUserName(new String(this.userName)); 
          if (this.password != null)
               cloned.setPassword(new String(this.password)); 
          return cloned;
      }
  
  
  
      /** 
       * getDaogenVersion will return information about
       * generator which created these sources.
       */
      public String getDaogenVersion() {
          return "DaoGen version 2.4.1";
      }
  
  }