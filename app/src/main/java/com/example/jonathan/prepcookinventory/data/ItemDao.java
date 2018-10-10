package com.example.jonathan.prepcookinventory.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * from item_table")
    LiveData<List<Item>> getAllItems();

    @Query("UPDATE item_table SET quantity=:quantity WHERE id = :id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE item_table SET quantity = :quantity")
    void zeroDatabase(int quantity);



}
