package com.example.jonathan.prepcookinventory.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Item.class, Order.class}, version = 4, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract OrderDao orderDao();


    // Make This Class SINGLETON to prevent multiple instances
    private static volatile ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create Database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    // Callback to create database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
            Log.d("RV", "Populating Async Database");
        }

    };

    // delete and populate database
    private static class PopulateDBAsync extends AsyncTask<Void,Void,Void> {
        private final ItemDao mDao;
        private final OrderDao mOrder;

        PopulateDBAsync(ItemRoomDatabase db) {
            mDao = db.itemDao();
            mOrder = db.orderDao();
        }

        @Override
        protected  Void doInBackground(final Void... params) {

            // mDao.deleteAll(725);
            mDao.deleteAll();
            Log.d("RV", "Items being Added");
            Item item = new Item(725,"Whole Milk", "Fridge", "Dairy","Sysco",0);
            mDao.insert(item);
            item = new Item(725,"Ground Beef","Freezer","Meat","Teds",2);
            mDao.insert(item);
            item = new Item(725,"Cheese","Freezer","Meat","Teds",2);
            mDao.insert(item);

            mOrder.deleteAllOrders();

            Order order = new Order("1/12/2018","FirstOrder");
            mOrder.insert(order);
            order = new Order(725, "12/1/2018", "Test Order");
            mOrder.insert(order);

            return null;

        }
    }


}
