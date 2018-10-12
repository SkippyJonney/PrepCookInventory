package com.example.jonathan.prepcookinventory.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class OrderRepository {
    private OrderDao mOrderDao;

    private LiveData<List<Order>> mAllOrders;

    OrderRepository(Application application) {

    }
}
