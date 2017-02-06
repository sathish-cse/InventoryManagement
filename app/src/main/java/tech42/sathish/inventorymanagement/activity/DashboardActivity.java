package tech42.sathish.inventorymanagement.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.firebasehelper.ProductStorageHelper;
import tech42.sathish.inventorymanagement.item.DashboardItemObject;
import tech42.sathish.inventorymanagement.recyclerviewdashboard.DashboardAdapter;


public class DashboardActivity extends ActionBarActivity {

    private DatabaseReference databaseReference;
    private GridLayoutManager gridLayoutManager;
    Integer computerProductsCount = 0, chemistryProductsCount = 0, storeProductsCount = 0, totalProductsCount = 0;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeFirebaseDatabase();

    }

    private void initializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.USERMAIL);
        getstoreProductsCount();
        getcomputerProductsCount();
        getchemistryProductsCount();

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
        rcAdapter.notifyDataSetChanged();
    }

    private List<DashboardItemObject> getAllItem(){

        List<DashboardItemObject> allItems = new ArrayList<DashboardItemObject>();
        allItems.add(new DashboardItemObject(Constant.COMPUTERLAB, computerProductsCount.toString()));
        allItems.add(new DashboardItemObject(Constant.CHEMISTRYLAB, chemistryProductsCount.toString()));
        allItems.add(new DashboardItemObject(Constant.STORELAB , storeProductsCount.toString()));
        allItems.add(new DashboardItemObject(Constant.TOTAL, totalProductsCount.toString()));
        return allItems;
    }


    private void getcomputerProductsCount()
    {
        DatabaseReference getItemListener = databaseReference.child(Constant.COMPUTER).child(Constant.PRODUCT);
        getItemListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    computerProductsCount = (int) dataSnapshot.getChildrenCount();
                }
                else
                {
                    computerProductsCount = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getchemistryProductsCount()
    {
        DatabaseReference getItemListener = databaseReference.child(Constant.CHEMISTRY).child(Constant.PRODUCT);
        getItemListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    chemistryProductsCount = (int) dataSnapshot.getChildrenCount();
                    totalProductsCount = chemistryProductsCount + computerProductsCount + storeProductsCount;
                    initializeRecyclerViews();
                }
                else
                {
                    chemistryProductsCount = 0;
                    totalProductsCount = chemistryProductsCount + computerProductsCount + storeProductsCount;
                    initializeRecyclerViews();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getstoreProductsCount()
    {
        DatabaseReference getItemListener = databaseReference.child(Constant.STORE).child(Constant.PRODUCT);
        getItemListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    storeProductsCount = (int) dataSnapshot.getChildrenCount();
                }
                else
                {
                    storeProductsCount = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
