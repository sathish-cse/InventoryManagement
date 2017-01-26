package tech42.sathish.inventorymanagement.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.item.ItemObject;
import tech42.sathish.inventorymanagement.recyclerview.RecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findToolbarView();
        findNavigationDrawer();
        findRecyclerViews();
        getInstance();
    }

    private void getInstance()
    {
        // Get Instance for firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Get Instance
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void findToolbarView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constant.TITLE);
    }

    public void findNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        finish();
                        break;
                    case R.id.logout:
                        logout();

                }
                return true;
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void findRecyclerViews()
    {
        List<ItemObject> rowListItem = getAllItemList();
        gridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(HomeActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject(Constant.IMPORT, R.drawable.in));
        allItems.add(new ItemObject(Constant.EXPORT, R.drawable.out));
        allItems.add(new ItemObject(Constant.MOVE, R.drawable.move));
        allItems.add(new ItemObject(Constant.LOCAITONS, R.drawable.locations));
        allItems.add(new ItemObject(Constant.PRODUCTS, R.drawable.products));
        allItems.add(new ItemObject(Constant.REPORT, R.drawable.report));

        return allItems;
    }

    private void logout() {
        setUserOffline();
        firebaseAuth.signOut();
    }

    private void setUserOffline() {
        if(firebaseAuth.getCurrentUser()!=null ) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            databaseReference.child(userId).child("connection").setValue("offline");
            goToLogin();
        }
    }

    private void goToLogin()
    {
        Intent gotoLogin = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(gotoLogin);
        finish();
    }
}
