package com.example.jonathan.prepcookinventory.data;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 *  REPOSITORY - NOT BEING USED -- :)
 *
 *   Use this class as a way to access data from local storage
 *   or from the internet. Currently I am not using this to its
 *   full potential.
 */
public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<String>> mLocations;
    private LiveData<List<String>> mCategories;

    ItemRepository(Application application, int orderID) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems(orderID);
        mLocations = mItemDao.getLocations(orderID);
        mCategories = mItemDao.getCategories(orderID);
    }

    /*
        Wrappers for SQL statements
     */
    LiveData<List<Item>> getAllItems(int orderID) {
        return mAllItems;
    }
    LiveData<List<String>> getCategories(int orderID) { return mCategories; }
    LiveData<List<String>> getLocations(int orderID) { return mLocations; }
    public void updateQuantity(int id, int quantity,int orderID) { new updateQuantityAsyncTask(mItemDao).execute(id,quantity, orderID);}
    public void zeroDatabase(int quantity,int orderID) { new zeroDatabaseAsyncTask(mItemDao).execute(orderID);}
    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    // Async to Update quantity
    private static class updateQuantityAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ItemDao mAsyncItemDao;

        updateQuantityAsyncTask(ItemDao dao) {
            this.mAsyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer...params) {
            mAsyncItemDao.updateQuantity(params[0], params[1], params[2]);
            return null;
        }

    }

    // Async to Update quantity
    private static class zeroDatabaseAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ItemDao mAsyncItemDao;

        zeroDatabaseAsyncTask(ItemDao dao) {
            this.mAsyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer...params) {
            mAsyncItemDao.zeroDatabase(0, params[0]);
            return null;
        }

    }

    // Async Task to Insert Data
    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncItemDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncItemDao.insert(params[0]);
            return null;
        }
    }

}
