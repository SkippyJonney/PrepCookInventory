package com.example.jonathan.prepcookinventory.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonathan.prepcookinventory.R;
import com.example.jonathan.prepcookinventory.SelectOrderFragment.OnListFragmentInteractionListener;
import com.example.jonathan.prepcookinventory.data.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    // Adapter Fields
    private List<Order> mValues;
    private final OnListFragmentInteractionListener mListener;

    public OrderListAdapter(Context context, OnListFragmentInteractionListener listener) {
        mListener = listener;
        mValues = new ArrayList<>();
    }

    public void setOrders(List<Order> orders) {
        mValues = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDate.setText(mValues.get(position).getOrderDate());
        holder.mName.setText(mValues.get(position).getOrderName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDate;
        public final TextView mName;
        public Order mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDate = view.findViewById(R.id.order_date);
            mName = view.findViewById(R.id.order_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
