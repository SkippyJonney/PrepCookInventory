package com.example.jonathan.prepcookinventory.data;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *  REPOSITORY - NOT BEING USED -- :)
 *
 *   Use this class as a way to access data from local storage
 *   or from the internet. Currently I am not using this to its
 *   full potential.
 */
public class ItemRepository {
    private ItemDao mItemDao;
    private OrderDao mOrderDao;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<String>> mLocations;
    private LiveData<List<String>> mCategories;
    private LiveData<List<Order>> mAllOrders;

    ItemRepository(Application application, int orderID) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mOrderDao = db.orderDao();
        mAllItems = mItemDao.getAllItems(orderID);
        mLocations = mItemDao.getLocations(orderID);
        mCategories = mItemDao.getCategories(orderID);
    }

    /*
        Wrappers for SQL statements
     */
    LiveData<List<Order>> getAllOrders() {
        return mOrderDao.getAllOrders();
    }
    List<Item> exportItems() {
        List<Item> items;
        try {
            items = new getItemsAsyncTask(mItemDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            items = null;
        }
        return items;
    }
    List<Order> exportOrders() {
        List<Order> orders;
        try {
            orders = new getOrdersAsyncTask(mOrderDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            orders = null;
        }
        return orders;
    }
    LiveData<List<Item>> getAllItems(int orderID) {
        return mItemDao.getAllItems(orderID);
    }
    LiveData<List<String>> getCategories(int orderID) { return mItemDao.getCategories(orderID); }
    LiveData<List<String>> getLocations(int orderID) { return mItemDao.getLocations(orderID); }
    public void updateQuantity(int id, int quantity,int orderID) { new updateQuantityAsyncTask(mItemDao).execute(id,quantity, orderID);}
    public void zeroDatabase(int quantity,int orderID) { new zeroDatabaseAsyncTask(mItemDao).execute(orderID);}
    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }
    public void insertOrder(Order order) { new insertAsyncOrder(mOrderDao).execute(order); }
    LiveData<Order> getOrderById(int id) { return mOrderDao.getById(id); }


    // Async Get Items
    private static class getItemsAsyncTask extends AsyncTask<Void,Void,List<Item>> {
        private ItemDao mAsyncItemDao;
        getItemsAsyncTask(ItemDao dao) { this.mAsyncItemDao = dao; }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            return mAsyncItemDao.exportItems();
        }
    }
    // Async Get Orders
    private static class getOrdersAsyncTask extends AsyncTask<Void,Void,List<Order>> {
        private OrderDao mAsyncOrderDao;
        getOrdersAsyncTask(OrderDao dao) { this.mAsyncOrderDao = dao;}

        @Override
        protected List<Order> doInBackground(Void... voids) {
            return mAsyncOrderDao.exportOrders();
        }
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

    private static class insertAsyncOrder extends AsyncTask<Order, Void, Void> {
        private OrderDao mAsyncOrderDao;

        insertAsyncOrder(OrderDao dao) { mAsyncOrderDao = dao; }

        @Override
        protected Void doInBackground(final Order... params) {
            mAsyncOrderDao.insert(params[0]);
            return null;
        }
    }


}
