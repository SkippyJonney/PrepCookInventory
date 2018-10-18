package com.example.jonathan.prepcookinventory;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jonathan.prepcookinventory.ui.OnFragmentActionListener;

public class EditItemFragment extends Fragment {

    // Fragment Communication
    private String ADD_KEY = "ITEM_ADD";
    private String CANCEL_KEY = "CANCEL";

    private OnFragmentActionListener mListener;

    private EditText mNameView;
    private EditText mCategoryView;
    private EditText mLocationView;
    private EditText mVendorView;
    private EditText mQuantityView;


    public EditItemFragment() {
        // Required empty public constructor
    }

    /*
    public static EditItemFragment newInstance() {
        return new EditItemFragment();
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        // Get References
        mNameView = view.findViewById(R.id.editText_item_name);
        mCategoryView = view.findViewById(R.id.editText_item_category);
        mLocationView = view.findViewById(R.id.editText_item_location);
        mVendorView = view.findViewById(R.id.editText_item_vendor);
        mQuantityView = view.findViewById(R.id.editText_item_quantity);


        // Setup Buttons
        final Button buttonSubmit = view.findViewById(R.id.btn_edit_order);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // use ternary operator to set default values
                if(!TextUtils.isEmpty(mNameView.getText().toString())) {

                    String name     = mNameView.getText().toString();
                    String location = mLocationView.getText().toString();
                    String category = mCategoryView.getText().toString();
                    String vendor   = mVendorView.getText().toString();
                    String quantity = mQuantityView.getText().toString();


                    String[] values = new String[5];
                    values[0] = (TextUtils.isEmpty(name)) ? "no name" : name;
                    values[1] = (TextUtils.isEmpty(location)) ? "default" : location;
                    values[2] = (TextUtils.isEmpty(category)) ? "main" : category;
                    values[3] = (TextUtils.isEmpty(vendor)) ? "default" : vendor;
                    values[4] = (TextUtils.isEmpty(quantity)) ? "0" : quantity;

                    mListener.fragmentButtons(ADD_KEY, values);
                }
                // else do nothing
            }
        });
        final Button buttonCancel = view.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mListener.fragmentButtons(CANCEL_KEY, null);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnFragmentActionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
