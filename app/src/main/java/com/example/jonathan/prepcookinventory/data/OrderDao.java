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
}
