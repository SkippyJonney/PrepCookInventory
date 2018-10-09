package com.example.jonathan.prepcookinventory;

        import android.arch.lifecycle.Observer;
        import android.arch.lifecycle.ViewModelProvider;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Debug;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.jonathan.prepcookinventory.data.Item;
        import com.example.jonathan.prepcookinventory.data.ItemListAdapter;
        import com.example.jonathan.prepcookinventory.data.ItemViewModel;

        import java.util.List;

        import static android.app.Activity.RESULT_OK;


public class ContentMainFragment extends Fragment  {


    private ItemViewModel mItemViewModel;
    private ItemListAdapter adapter;
    private RecyclerView recyclerView;
    private ItemClickListener mListener;

    public ContentMainFragment() {
        // Required empty public constructor
    }

    public static ContentMainFragment newInstance() {
        ContentMainFragment fragment = new ContentMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get view model
        populateAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new ItemListAdapter(this.getActivity(), new ItemListAdapter.buttonListener() {
            @Override
            public void onQuantityUpdate(int id, int quantity) {
                // do something
                Log.d("RV", Integer.toString(id) + " " + Integer.toString(quantity));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateAdapter();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            mListener = (ItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement ItemClickListener");
        }
    }

    // Populate adapter with observer
    private void populateAdapter() {
        mItemViewModel = ViewModelProviders.of(this.getActivity()).get(ItemViewModel.class);

        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>(){
            @Override
            public void onChanged(@Nullable final List<Item> items){
                // set adapter
                adapter.setItems(items);
            }
        });

        //recyclerView.setAdapter(adapter);
    }

    public interface ItemClickListener {
        void onQuantityChange(int id, int quantity);
    }


}
