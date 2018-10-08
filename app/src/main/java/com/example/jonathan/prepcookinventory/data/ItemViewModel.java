package com.example.jonathan.prepcookinventory.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.jonathan.prepcookinventory.data.Item;
import com.example.jonathan.prepcookinventory.data.ItemRepository;

import java.util.List;

// TODO SAVE INSTANCE STATE -- Make sure this survives stop() by using onSaveInstanceState()
public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<Item>> mAllItems;

    public ItemViewModel (Application application) {
        super(application);

        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    // Getter for liveData that hides implementation from UI
    public LiveData<List<Item>> getAllItems() { return mAllItems; }

    // call repository insert wrapper
    public void insert(Item item) { mRepository.insert(item); }
}
