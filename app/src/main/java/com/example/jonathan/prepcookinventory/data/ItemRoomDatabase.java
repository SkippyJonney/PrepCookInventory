package com.example.jonathan.prepcookinventory.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemRoomDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();


    // Make This Class SINGLETON to prevent multiple instances
    private static volatile ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create Database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
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
        }
    };

    // delete and populate database
    private static class PopulateDBAsync extends AsyncTask<Void,Void,Void> {
        private final ItemDao mDao;

        PopulateDBAsync(ItemRoomDatabase db) {
            mDao = db.itemDao();
        }

        @Override
        protected  Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Item item = new Item("Whole Milk", "Fridge", "Dairy","Sysco",0);
            mDao.insert(item);
            item = new Item("Ground Beef","Freezer","Meat","Teds",2);
            mDao.insert(item);
            return null;
        }
    }


}
