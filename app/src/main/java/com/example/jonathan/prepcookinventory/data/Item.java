package com.example.jonathan.prepcookinventory.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderID;
    private String name;
    private String location;
    private String category;
    private String vendor;
    private int quantity;

    // Constructor
    public Item(int orderID,
                @NonNull String name,
                @NonNull String location,
                @NonNull String category,
                @NonNull String vendor,
                int quantity )
    {
        this.orderID = orderID;
        this.name = name;
        this.location = location;
        this.category = category;
        this.vendor = vendor;
        this.quantity = quantity;
    }

    // Accessors
    public int getId() { return id; }
    public int getOrderID() { return orderID; }
    public String getName() { return name; }
    public String getLocation() { return location;}
    public String getCategory() { return category; }
    public String getVendor() { return vendor; }
    public int getQuantity() { return quantity; }

    public void setId(int id) { this.id = id; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setCategory(String category) { this.category = category;}
    public void setVendor(String vendor) { this.vendor = vendor;}
    public void setQuantity(int quantity) { this.quantity = quantity; }




}
