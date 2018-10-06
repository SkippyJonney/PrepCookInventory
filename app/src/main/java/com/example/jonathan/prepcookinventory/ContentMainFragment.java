package com.example.jonathan.prepcookinventory;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


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


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
