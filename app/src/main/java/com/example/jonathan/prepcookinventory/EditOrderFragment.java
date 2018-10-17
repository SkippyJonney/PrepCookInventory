package com.example.jonathan.prepcookinventory;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jonathan.prepcookinventory.ui.OnFragmentActionListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;

public class EditOrderFragment extends Fragment {
    public EditOrderFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    // Fragment Communication
    private String ORDER_KEY = "ORDER_ADD";
    private String CANCEL_KEY = "CANCEL";
    private OnFragmentActionListener mListener;

    private EditText mOrderName;
    private DatePicker mDatePicker;
    private AdView mAdView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_order, container, false);

        // Get Refs
        mOrderName = view.findViewById(R.id.order_name);
        mDatePicker = view.findViewById(R.id.datePicker);


        final Button buttonSubmit = view.findViewById(R.id.btn_edit_order);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mOrderName.getText().toString();

                StringBuilder date = new StringBuilder();
                date.append((mDatePicker.getMonth() + 1) + "/");
                date.append(mDatePicker.getDayOfMonth()+ "/");
                date.append(mDatePicker.getYear());

                String[] values = new String[2];
                values[0] = (TextUtils.isEmpty(name)) ? "" : name;
                values[1] = date.toString();

                mListener.fragmentButtons(ORDER_KEY, values);
            }
        });

        final Button buttonCancel = view.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.fragmentButtons(CANCEL_KEY, null);
            }
        });


        // Implement Ads
        mAdView = view.findViewById(R.id.adView_edit_order_banner);
        AdRequest BannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(BannerRequest);


        return view;
    }

    // Attach Listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnFragmentActionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must Implement OnFragmentInteractionListener");
        }
    }
}
