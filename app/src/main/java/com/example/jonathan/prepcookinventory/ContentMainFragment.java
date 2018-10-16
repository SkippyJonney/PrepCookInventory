package com.example.jonathan.prepcookinventory;

        import android.arch.lifecycle.Observer;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Spinner;

        import com.example.jonathan.prepcookinventory.data.Item;
        import com.example.jonathan.prepcookinventory.data.ItemListAdapter;
        import com.example.jonathan.prepcookinventory.data.ItemViewModel;

        import java.util.ArrayList;
        import java.util.List;


public class ContentMainFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private ItemViewModel mItemViewModel;
    private ItemListAdapter adapter;
    private RecyclerView recyclerView;
    private ItemClickListener mListener;

    private Spinner locationSpinner;
    private Spinner categorySpinner;
    private List<String> mLocations;
    private List<String> mCategories;
    private View spinnerContainer;

    private int orderID;
    public void setOrderID(int orderID) { this.orderID = orderID; }

    /*
    public ContentMainFragment() {
        // Required empty public constructor
    }

    public static ContentMainFragment newInstance() {
        ContentMainFragment fragment = new ContentMainFragment();

        return fragment;
    }
    */
    public ContentMainFragment() {}

    public static ContentMainFragment newInstance(int orderID) {
        Bundle bundle = new Bundle();
        bundle.putInt("orderID", orderID);

        ContentMainFragment fragment = new ContentMainFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
    private void readBundle(Bundle bundle) {
        if(bundle != null) {
            orderID = bundle.getInt("orderID");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get view model
        populateAdapter();

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_main, container, false);

        // Get Arguments
        readBundle(getArguments());

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new ItemListAdapter(this.getActivity(), new ItemListAdapter.buttonListener() {
            @Override
            public void onQuantityUpdate(int id, int quantity) {
                // do something
                Log.d("RV", Integer.toString(id) + " " + Integer.toString(quantity));
                mListener.onQuantityChange(id,quantity, orderID);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // Inflate locationSpinner
        spinnerContainer = view.findViewById(R.id.spinner_container);
        locationSpinner = (Spinner) view.findViewById(R.id.location_spinner);
        categorySpinner = (Spinner) view.findViewById(R.id.category_spinner);
        //String animalList[] = {"Lion","Tiger","Monkey","Elephant","Dog","Cat","Camel"};
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, animalList);
        //locationSpinner.setAdapter(arrayAdapter);

        mLocations = new ArrayList<>();
        mLocations.add("Filter Location");
        mItemViewModel.getmLocations(orderID).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> locations) {
                for(String str : locations) {  if(!mLocations.contains(str)) { mLocations.add(str);  } } }
        });

        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, mLocations);
        locationSpinner.setAdapter(adapterLocations);
        locationSpinner.setOnItemSelectedListener(this);

        mCategories = new ArrayList<>();
        mCategories.add("Filter Categories");
        mItemViewModel.getmCategories(orderID).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> cats) {
                for(String str : cats) {  if(!mCategories.contains(str)) { mCategories.add(str);  } } }
        });
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, mCategories);
        categorySpinner.setAdapter(adapterCategories);
        categorySpinner.setOnItemSelectedListener(this);

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

        mItemViewModel.getAllItems(orderID).observe(this, new Observer<List<Item>>(){
            @Override
            public void onChanged(@Nullable final List<Item> items){
                // set adapter
                adapter.setItems(items);

            }
        });

        //recyclerView.setAdapter(adapter);
    }

    public interface ItemClickListener {
        void onQuantityChange(int id, int quantity, int orderID);
    }

    // filter interface
    public void filterAdapter(String query) {
        adapter.getFilter().filter(query);
        spinnerContainer.animate().translationY(-spinnerContainer.getHeight());
        locationSpinner.setSelection(0);
        categorySpinner.setSelection(0);
    }

    public void unFilterAdapter() {
        adapter.cancelFilter();
        spinnerContainer.animate().translationY(spinnerContainer.getHeight());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tmp = parent.getItemAtPosition(position).toString();
        Log.d("RV", "Filter " + tmp);
        adapter.getFilter().filter(tmp);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        adapter.getFilter().filter("filter");
    };

}
