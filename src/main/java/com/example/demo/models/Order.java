package com.example.demo.models;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class Order implements Cloneable, Serializable{
    private String productName;
    private int quantity;
    public String getProductName()
    {
        return this.productName;
    }
    public int getQuantity()
    {
        return this.quantity;
    }
    public void setProductName(String productName)
    {
        this.productName=productName;
    }
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }
}   