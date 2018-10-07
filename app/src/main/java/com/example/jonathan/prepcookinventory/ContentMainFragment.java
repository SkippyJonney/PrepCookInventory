package com.example.jonathan.prepcookinventory;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.jonathan.prepcookinventory.data.ItemListAdapter;


public class ContentMainFragment extends Fragment {

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this.getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
