package com.example.jonathan.prepcookinventory.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert()
    void insert(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrders();

    @Query("DELETE FROM order_table WHERE orderName=:name")
    void deleteOrder(String name);

    @Query("DELETE FROM order_table")
    void deleteAllOrders();

    @Query("SELECT * FROM order_table WHERE orderID=:id")
    LiveData<Order> getById(int id);
}
