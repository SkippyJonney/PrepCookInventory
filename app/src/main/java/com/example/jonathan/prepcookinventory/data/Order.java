package com.example.jonathan.prepcookinventory.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "order_table")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderID;
    private Date orderDate;
    private String orderName;

    public Order(Date date, String orderName) {
        this.orderDate = date;
        this.orderName = orderName;
    }

    public int getOrderID() { return orderID;}
    public Date getOrderDate() { return orderDate; }
    public String getOrderName() { return orderName; }

    public void setOrderID(int id) { orderID = id; }
    public void setOrderDate(Date date) { orderDate = date; }
    public void setOrderName(String name) { orderName = name; }
}
