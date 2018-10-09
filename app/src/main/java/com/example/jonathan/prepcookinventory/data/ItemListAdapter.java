package com.example.jonathan.prepcookinventory.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonathan.prepcookinventory.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    // Define View Holder with inventory button listeners
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_name;
        private final TextView tv_cat;
        private final TextView tv_quantity;
        private int id;
        private int quantity;
        private final Button addButton;
        private final Button subButton;

        private ItemViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_item_name);
            tv_cat = itemView.findViewById(R.id.tv_item_category);
            tv_quantity = itemView.findViewById(R.id.tv_item_quantity);
            addButton = itemView.findViewById(R.id.btn_add);
            subButton = itemView.findViewById(R.id.btn_sub);

            // TODO A2 -Setup Click Listeners
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        myButtonListener.onQuantityUpdate(id, ++quantity);
                }});
            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        myButtonListener.onQuantityUpdate(id, --quantity);
                }
                });
            }
    }

    // Adapter Fields
    private final LayoutInflater mInflater;
    private List<Item> mItems; // cache of items - copied
    public buttonListener myButtonListener;

    public ItemListAdapter(Context context, buttonListener listener) {
        mInflater = LayoutInflater.from(context);
        myButtonListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if(mItems != null) {
            Item current = mItems.get(position);
            holder.tv_name.setText(current.getName());
            holder.tv_cat.setText(current.getCategory());
            holder.tv_quantity.setText(Integer.toString(current.getQuantity()));
            holder.id = current.getId();
            holder.quantity = current.getQuantity();
        } else {
            // data not ready
            holder.tv_name.setText("No Item");
        }
    }


    public void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    // Return 0 if not initialized
    @Override
    public int getItemCount() {
        if(mItems != null)
            return mItems.size();
        else return 0;
    }


    public interface buttonListener {
        void onQuantityUpdate(int id,int quantity);
    }
}

