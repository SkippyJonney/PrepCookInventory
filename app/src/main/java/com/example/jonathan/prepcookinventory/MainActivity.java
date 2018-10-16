package com.example.jonathan.prepcookinventory;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jonathan.prepcookinventory.data.CustomViewModelFactory;
import com.example.jonathan.prepcookinventory.data.Item;
import com.example.jonathan.prepcookinventory.data.ItemListAdapter;
import com.example.jonathan.prepcookinventory.data.ItemViewModel;
import com.example.jonathan.prepcookinventory.data.Order;
import com.example.jonathan.prepcookinventory.ui.OnFragmentActionListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentActionListener,
        ContentMainFragment.ItemClickListener,
        AdapterView.OnItemSelectedListener,
        SelectOrderFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawer;

    private SearchView searchView;

    // Fragment Transactions
    private FragmentManager fragmentManager;
    private String FRAGMENT_EDIT_ITEM="50";
    private String FRAGMENT_CONTENT_MAIN="10";
    private String FRAGMENT_EDIT_ORDER="42";
    private String FRAGMENT_SELECT_ORDER="20";

    // Recycler View
    private ItemViewModel mItemViewModel;

    private int TEST_ORDER_ID = 725;
    private String ORDER_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Order ID
        if(savedInstanceState != null) {
            TEST_ORDER_ID = savedInstanceState.getInt(ORDER_ID);
        }


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Integer.toString(TEST_ORDER_ID));

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

        mItemViewModel = ViewModelProviders.of(this, new CustomViewModelFactory(this.getApplication(), TEST_ORDER_ID)).get(ItemViewModel.class);


        // Add Fragments
        fragmentManager = getSupportFragmentManager();
        ContentMainFragment contentMainFragment = ContentMainFragment.newInstance(TEST_ORDER_ID);
        fragmentManager.beginTransaction()
                .add(R.id.main_framgent, contentMainFragment, FRAGMENT_CONTENT_MAIN)
                .commit();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // start with drawer open
        //drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        /*

        IMPLEMENT SEARCHABLE

         */
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_items)
                .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // listen to filter changes
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter adapter
                Log.d("RV", "Query Submit");
                ContentMainFragment fragment = (ContentMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CONTENT_MAIN);
                if( fragment != null) {
                    // if available
                    fragment.filterAdapter(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter adapter ???
                Log.d("RV", "Query Change");
                ContentMainFragment fragment = (ContentMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CONTENT_MAIN);
                if( fragment != null) {
                    // if available
                    fragment.filterAdapter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("RV", "Query Close");
                ContentMainFragment fragment = (ContentMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CONTENT_MAIN);
                if( fragment != null) {
                    // if available
                    fragment.unFilterAdapter();
                }
                return false;
            }
        });


        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "You Have " + label, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Do nothing!!
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
            swapFragments(FRAGMENT_EDIT_ORDER);
        } else if (id == R.id.nav_open_inventory) {
            swapFragments(FRAGMENT_SELECT_ORDER);
        } else if (id == R.id.nav_zero) {
            mItemViewModel.zeroDatabase(TEST_ORDER_ID);
        } else if (id == R.id.nav_export) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Define Fragment Interactions
    @Override
    public void fragmentButtons(String action, String[] data) {

        switch(action) {
            case "ITEM_ADD":
                // Add item to database
                Item newItem = new Item(TEST_ORDER_ID, data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                mItemViewModel.insert(newItem);
                // swap fragments
                swapFragments(FRAGMENT_CONTENT_MAIN);
                break;
            case "ORDER_ADD":
                Order newOrder = new Order(data[1], data[0]);
                mItemViewModel.insert(newOrder);
                // gracefully waterfall to cancel
                // no break
                TEST_ORDER_ID = newOrder.getOrderID();
                mItemViewModel.setOrderId(newOrder.getOrderID());
                this.recreate();
            case "CANCEL":
                swapFragments(FRAGMENT_CONTENT_MAIN);
            default:
                break;
        }
    }

    @Override
    public void onQuantityChange(int id, int quantity, int orderID) {
        // Increase quantity
        mItemViewModel.updateQuantity(id,quantity,orderID);
    }

    @Override
    public void onListFragmentInteraction(Order order) {
        // Set Order ID
        TEST_ORDER_ID = order.getOrderID();
        // Refresh data
        //swapFragments(FRAGMENT_CONTENT_MAIN);
        //clearStack();
        mItemViewModel.setOrderId(TEST_ORDER_ID);
        this.recreate();
    }


    // Swap Fragments in View & Check for Existing Fragment
    public void swapFragments(String swap_to_key) {

        Fragment destinationFragment = fragmentManager.findFragmentByTag(swap_to_key);
        if(destinationFragment == null) {
            // create desired fragment
            if(swap_to_key == FRAGMENT_CONTENT_MAIN) {
                destinationFragment = ContentMainFragment.newInstance(TEST_ORDER_ID);
            } else if(swap_to_key == FRAGMENT_EDIT_ITEM) {
                destinationFragment = new EditItemFragment();
            } else if(swap_to_key == FRAGMENT_EDIT_ORDER) {
                destinationFragment = new EditOrderFragment();
            }  else if(swap_to_key == FRAGMENT_SELECT_ORDER) {
                destinationFragment = new SelectOrderFragment();
            }

            // TODO ENSURE NO NULL FRAGMENT
        }
        fragmentManager.beginTransaction()
                .replace(R.id.main_framgent, destinationFragment, swap_to_key)
                .addToBackStack(null)
                .commit();


    }

    // Save and Restore Order ID
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(ORDER_ID,TEST_ORDER_ID);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        TEST_ORDER_ID = state.getInt(ORDER_ID);
    }

}
