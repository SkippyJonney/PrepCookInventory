package com.example.jonathan.prepcookinventory;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jonathan.prepcookinventory.data.Item;
import com.example.jonathan.prepcookinventory.data.ItemListAdapter;
import com.example.jonathan.prepcookinventory.data.ItemViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EditItemFragment.OnFragmentInteractionListener,
        ContentMainFragment.ItemClickListener {

    private DrawerLayout drawer;

    // Fragment Transactions
    private FragmentManager fragmentManager;
    private String FRAGMENT_EDIT_ITEM="50";
    private String FRAGMENT_CONTENT_MAIN="10";

    // Recycler View
    private ItemViewModel mItemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Inventory");

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        //mItemViewModel = ViewModelProviders.of(this.getActivity()).get(ItemViewModel.class);

        // Add Fragments
        fragmentManager = getSupportFragmentManager();
        ContentMainFragment contentMainFragment = new ContentMainFragment();
        fragmentManager.beginTransaction()
                .add(R.id.main_framgent, contentMainFragment, FRAGMENT_CONTENT_MAIN)
                .commit();



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // start with drawer open
        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         *  Add Recycler View Code Here
         *
         *  -- maybe this code should go in a fragment
         */
        //RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //final ItemListAdapter adapter = new ItemListAdapter(this);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /* Needed to do action menu
        if (id == R.id.action_settings) {
            return true;
        }
        */
        if(id == R.id.home) {
            swapFragments(FRAGMENT_CONTENT_MAIN);
            return true;
        }
        if(id == R.id.add_item) {
            swapFragments(FRAGMENT_EDIT_ITEM);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_inventory) {
            // Handle the camera action
        } else if (id == R.id.nav_open_inventory) {
            // TODO Implement Export
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Define Fragment Interactions
    @Override
    public void editItemFragmentButton(String action, String[] data) {
        if( action == "ADD" ) {
            Log.d("RV", data[0].toString() + " from fragment");

            // Add item to database
            Item newItem = new Item(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
            mItemViewModel.insert(newItem);
            // swap fragments
            swapFragments(FRAGMENT_CONTENT_MAIN);

        } else if( action == "CANCEL" ) {
            Log.d("RV", "Cancel close Fragment");
            swapFragments(FRAGMENT_CONTENT_MAIN);
        }
    }

    @Override
    public void onQuantityChange(int id, int quantity) {
        // Increase quantity
        mItemViewModel.updateQuantity(id,quantity);
    }


    // Swap Fragments in View & Check for Existing Fragment
    public void swapFragments(String swap_to_key) {

        Fragment destinationFragment = fragmentManager.findFragmentByTag(swap_to_key);
        if(destinationFragment == null) {
            // create desired fragment
            if(swap_to_key == FRAGMENT_CONTENT_MAIN) {
                destinationFragment = new ContentMainFragment();
            } else if(swap_to_key == FRAGMENT_EDIT_ITEM) {
                destinationFragment = new EditItemFragment();
            }
        }
        fragmentManager.beginTransaction()
                .replace(R.id.main_framgent, destinationFragment, swap_to_key)
                .addToBackStack(null)
                .commit();


    }


}
