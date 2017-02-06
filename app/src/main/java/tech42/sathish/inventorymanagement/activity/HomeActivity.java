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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.item.HomeItemObject;
import tech42.sathish.inventorymanagement.recyclerviewhome.RecyclerViewAdapter;
import tech42.sathish.inventorymanagement.recyclerviewhome.RecyclerViewList;

public class HomeActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    public static String USERMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeToolbarView();
        initializeRecyclerViews();
        initializeInstance();
        initializeNavigationDrawer();
    }

    private void initializeInstance()
    {
        // Get Instance for firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Get Instance
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // GET USER MAIL ID
        USERMAIL = firebaseAuth.getCurrentUser().getEmail().replace(".","_");
    }

    private void initializeToolbarView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constant.TITLE);
    }

    public void initializeNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Intent home = new Intent(HomeActivity.this, LabSelectionActivity.class);
                        startActivity(home);
                        break;
                    case R.id.logout:
                        logout();

                }
                return true;
            }
        });

        View navigationHeaderView = navigationView.getHeaderView(0);
        TextView txt_Email = (TextView) navigationHeaderView.findViewById(R.id.id);
        TextView txt_Title = (TextView) navigationHeaderView.findViewById(R.id.name);
        txt_Title.setText(Constant.STORAGE);
        txt_Email.setText(firebaseAuth.getCurrentUser().getEmail());

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

    private void initializeRecyclerViews()
    {
        List<HomeItemObject> rowListItem = RecyclerViewList.getAllItem();
        gridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(HomeActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
