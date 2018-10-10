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

    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    // Wrapper for getAllItems();
    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }
    // Wrapper for updateQuantity();
    public void updateQuantity(int id, int quantity) { new updateQuantityAsyncTask(mItemDao).execute(id,quantity);}
    public void zeroDatabase(int quantity) { new zeroDatabaseAsyncTask(mItemDao).execute();}
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
            mAsyncItemDao.updateQuantity(params[0], params[1]);
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
            mAsyncItemDao.zeroDatabase(0);
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
