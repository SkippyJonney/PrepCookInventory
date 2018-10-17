package com.example.jonathan.prepcookinventory.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.jonathan.prepcookinventory.data.Item;
import com.example.jonathan.prepcookinventory.data.ItemRepository;

import java.util.List;

// TODO SAVE INSTANCE STATE -- Make sure this survives stop() by using onSaveInstanceState()
public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<String>> mLocations;
    private LiveData<List<String>> mCategories;
    private LiveData<List<Order>> mOrders;

    private int mORDER_ID = 0;


    public ItemViewModel (Application application, int orderID) {
        super(application);
        mORDER_ID = orderID;
        mRepository = new ItemRepository(application,orderID);
        mOrders = mRepository.getAllOrders();
}

    // Getter for liveData that hides implementation from UI
    public LiveData<List<Item>> getAllItems(int orderID) {
        return mRepository.getAllItems(mORDER_ID);
    }

    public LiveData<List<String>> getmCategories(int orderID) {
        return mRepository.getCategories(mORDER_ID);
    }

    public LiveData<List<String>> getmLocations(int orderID) {
        return  mRepository.getLocations(mORDER_ID);
    }

    public LiveData<List<Order>> getmOrders() { return mOrders; }

    public LiveData<Order> getOrderById(int id) { return mRepository.getOrderById(id); }

    // call repository insert wrapper
    public void insert(Item item) { mRepository.insert(item); }
    public void insert(Order order) { mRepository.insertOrder(order); }
    public void zeroDatabase(int orderID) { mRepository.zeroDatabase(0, orderID);}

    //call repository for update quantity
    public void updateQuantity(int id, int quantity, int orderID) { mRepository.updateQuantity(id,quantity, orderID);}

    public void setOrderId(int id) { mORDER_ID = id; }
}
