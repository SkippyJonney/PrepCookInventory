package com.example.jonathan.prepcookinventory.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert()
    void insert(Item item);

    @Query("DELETE FROM item_table WHERE orderID=:orderID")
    void deleteAllItems(int orderID);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * FROM item_table")
    List<Item> exportItems();

    @Query("SELECT * from item_table WHERE orderID=:orderID")
    LiveData<List<Item>> getAllItems(int orderID);

    // Get Categories for spinner
    @Query("SELECT DISTINCT category from item_table WHERE orderID=:orderID")
    LiveData<List<String>> getCategories(int orderID);

    // Get Locations for spinner
    @Query("SELECT DISTINCT location from item_table WHERE orderID=:orderID")
    LiveData<List<String>> getLocations(int orderID);

    @Query("UPDATE item_table SET quantity=:quantity WHERE id = :id AND orderID=:orderID")
    void updateQuantity(int id, int quantity, int orderID);

    @Query("UPDATE item_table SET quantity = :quantity WHERE orderID=:orderID")
    void zeroDatabase(int quantity, int orderID);



}
