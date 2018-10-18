package com.example.jonathan.prepcookinventory.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "order_table")
public class Order {

    public Order() {}

    @PrimaryKey(autoGenerate = true)
    private int orderID;
    private String orderDate;
    private String orderName;

    public Order(String date, String orderName) {
        this.orderDate = date;
        this.orderName = orderName;
    }

    public Order(int orderID, String orderDate, String orderName) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderName = orderName;
    }

    public int getOrderID() { return orderID;}
    public String getOrderDate() { return orderDate; }
    public String getOrderName() { return orderName; }
    public String getOrderTitle() {
        return (orderName == "") ? orderDate : orderName;
    }

    public void setOrderID(int id) { orderID = id; }
    public void setOrderDate(String dateStr) { orderDate = dateStr; }
    public void setOrderName(String name) { orderName = name; }
}
