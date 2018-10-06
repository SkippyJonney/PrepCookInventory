package com.example.jonathan.prepcookinventory.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonathan.prepcookinventory.R;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemItemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Item> mItems; // cache of items - copied

    public ItemListAdapter(Context context) { mInflater = LayoutInflater.from(context);}

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if(mItems != null) {
            Item current = mItems.get(position);
        } else {
            // data not ready
            holder.itemItemView.setText("No Item");
        }
    }


    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    // Return 0 if not initialized
    @Override
    public int getItemCount() {
        if(mItems != null) {
            return mItems.size();
        } else return 0;
    }
}

