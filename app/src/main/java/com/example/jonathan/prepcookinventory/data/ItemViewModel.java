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


    public ItemViewModel (Application application, int orderID) {
        super(application);

        mRepository = new ItemRepository(application,orderID);
        mAllItems = mRepository.getAllItems(orderID);
        mLocations = mRepository.getLocations(orderID);
        mCategories = mRepository.getCategories(orderID);
    }

    // Getter for liveData that hides implementation from UI
    public LiveData<List<Item>> getAllItems(int orderID) {
        return mAllItems;
    }

    public LiveData<List<String>> getmCategories(int orderID) {
        return mCategories;
    }

    public LiveData<List<String>> getmLocations(int orderID) {
        return mLocations;
    }

    // call repository insert wrapper
    public void insert(Item item) { mRepository.insert(item); }
    public void zeroDatabase(int orderID) { mRepository.zeroDatabase(0, orderID);}

    //call repository for update quantity
    public void updateQuantity(int id, int quantity, int orderID) { mRepository.updateQuantity(id,quantity, orderID);}
}
