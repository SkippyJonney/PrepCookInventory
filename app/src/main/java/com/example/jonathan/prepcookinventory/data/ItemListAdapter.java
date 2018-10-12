package com.example.jonathan.prepcookinventory.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.jonathan.prepcookinventory.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter
        extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>
        implements Filterable {

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
    private List<String> mLocations;
    private List<String> mCategories;
    private List<Item> mItemsFiltered; // filtered items
    public buttonListener myButtonListener;

    public ItemListAdapter(Context context, buttonListener listener) {
        mInflater = LayoutInflater.from(context);
        myButtonListener = listener;
        mLocations = new ArrayList<>();
        mCategories = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if(mItemsFiltered != null) {
            Item current = mItemsFiltered.get(position);
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
        mItemsFiltered = mItems;
        notifyDataSetChanged();

        for(Item item : mItems) {
            if(!mLocations.contains(item.getLocation())) {
                mLocations.add(item.getLocation());
            }
            if(!mCategories.contains(item.getCategory())) {
                mCategories.add(item.getCategory());
            }
        }

    }

    // Return 0 if not initialized
    @Override
    public int getItemCount() {
        if(mItemsFiltered != null)
            return mItemsFiltered.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("RV", "Filtering");
                if (charString.isEmpty()) {
                    mItemsFiltered = mItems;
                    Log.d("RV", "empty query");
                } else if (charString.toLowerCase().contains("filter")) {
                    mItemsFiltered = mItems;
                } else  {
                    List<Item> filteredList = new ArrayList<>();
                    // Check if we are filtering Location || Category
                    if(mLocations.contains(charString)) {
                        for(Item row : mItems) {
                            if(row.getLocation().toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(row);
                        }
                    } else if ( mCategories.contains(charString)) {
                        for(Item row : mItems) {
                            if(row.getCategory().contains(charString))
                                filteredList.add(row);
                        }
                    } else {
                        for (Item row : mItems) {

                            // match condition
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }

                    mItemsFiltered = filteredList;
                    Log.d("RV", "Found " + Integer.toString(filteredList.size()));
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mItemsFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mItemsFiltered = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void cancelFilter() {
        // un-filter by swapping to original
        mItemsFiltered = mItems;
        notifyDataSetChanged();
    }

    public interface buttonListener {
        void onQuantityUpdate(int id,int quantity);
    }
}

