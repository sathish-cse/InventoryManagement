package tech42.sathish.inventorymanagement.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.customadapter.ProductCustomAdapter;
import tech42.sathish.inventorymanagement.item.DashboardItemObject;
import tech42.sathish.inventorymanagement.model.Product;
import tech42.sathish.inventorymanagement.recyclerviewdashboard.DashboardAdapter;


public class DashboardActivity extends ActionBarActivity {

    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeRecyclerViews();
    }

    private void initializeRecyclerViews()
    {
        List<DashboardItemObject> rowListItem = getAllItem();
        gridLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        DashboardAdapter rcAdapter = new DashboardAdapter(DashboardActivity.this,rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<DashboardItemObject> getAllItem(){

        List<DashboardItemObject> allItems = new ArrayList<DashboardItemObject>();
        allItems.add(new DashboardItemObject(Constant.IMPORTQTY, "1078"));
        allItems.add(new DashboardItemObject(Constant.EXPORTQTY, "1060"));
        allItems.add(new DashboardItemObject(Constant.IMPORTPRICE ,"150"));
        allItems.add(new DashboardItemObject(Constant.EXPORTPRICE, "5768"));
        return allItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String result) {
                // this is your adapter that will be filtered

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);
    }



}
