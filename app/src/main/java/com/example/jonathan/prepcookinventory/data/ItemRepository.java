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
    LiveData<List<Item>> getmAllItems() {
        return mAllItems;
    }

    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
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
