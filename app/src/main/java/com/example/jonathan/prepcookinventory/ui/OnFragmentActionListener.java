package com.example.jonathan.prepcookinventory.ui;

import android.support.annotation.Nullable;

public interface OnFragmentActionListener {

    //public void onFragmentMessage(String TAG, Object data);

    void fragmentButtons(String action, @Nullable String[] data);

}
