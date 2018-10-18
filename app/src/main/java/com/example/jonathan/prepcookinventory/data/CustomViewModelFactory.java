package com.example.jonathan.prepcookinventory.data;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private int orderID;
    private Application mApplication;

    public CustomViewModelFactory(Application application, int orderID) {
        mApplication = application;
        this.orderID = orderID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ItemViewModel(mApplication, orderID);
    }
}
