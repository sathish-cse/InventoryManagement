package tech42.sathish.inventorymanagement.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.customadapter.ProductCustomAdapter;
import tech42.sathish.inventorymanagement.model.Product;


public class ProductsActivity extends AppCompatActivity {

    private ProgressBar mProgressBarForProducts;
    private RecyclerView mProductRecyclerView;

    private String mCurrentUserUid;
    private List<String> mUsersKeyList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mProductDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ProductCustomAdapter ProductCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mProgressBarForProducts = (ProgressBar)findViewById(R.id.progress_bar_users);
        mProductRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_users);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Product Details..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initializeAuthInstance();
        initializeUsersDatabase();
        initializeUserRecyclerView();
        initializeUsersKeyList();
        initializesetAuthListener();

        progressDialog.dismiss();
    }



    private void initializeAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeUsersDatabase() {
        mProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.USERMAIL).child(Constant.PRODUCT);
    }

    private void initializeUserRecyclerView() {
        ProductCustomAdapter = new ProductCustomAdapter(this, new ArrayList<Product>());
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.setHasFixedSize(true);
        mProductRecyclerView.setAdapter(ProductCustomAdapter);
    }

    private void initializeUsersKeyList() {
        mUsersKeyList = new ArrayList<String>();
    }

    private void initializesetAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                hideProgressBarForUsers();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    setUserData(user);
                    queryAllUsers();
                } else {
                    // Product is signed out
                    goToLogin();
                }
            }
        };
    }

    private void setUserData(FirebaseUser user) {
        mCurrentUserUid = user.getUid();
    }

    private void queryAllUsers() {
        mChildEventListener = getChildEventListener();
        mProductDatabaseReference.limitToFirst(50).addChildEventListener(mChildEventListener);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressBarForUsers();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        clearCurrentUsers();

        if (mChildEventListener != null) {
            mProductDatabaseReference.removeEventListener(mChildEventListener);
        }

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void clearCurrentUsers() {
        ProductCustomAdapter.clear();
        mUsersKeyList.clear();
    }

   
    private void showProgressBarForUsers(){
        mProgressBarForProducts.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForUsers(){
        if(mProgressBarForProducts.getVisibility()==View.VISIBLE) {
            mProgressBarForProducts.setVisibility(View.GONE);
        }
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()){

                    String userUid = dataSnapshot.getKey();

                    if(dataSnapshot.getKey().equals(mCurrentUserUid)){
                        Product currentUser = dataSnapshot.getValue(Product.class);

                    }else {
                        Product recipient = dataSnapshot.getValue(Product.class);
                        recipient.setProductid(userUid);
                        mUsersKeyList.add(userUid);
                        ProductCustomAdapter.refill(recipient);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();
                    if(!userUid.equals(mCurrentUserUid)) {

                        Product user = dataSnapshot.getValue(Product.class);

                        int index = mUsersKeyList.indexOf(userUid);
                        if(index > -1) {
                            ProductCustomAdapter.changeUser(index, user);
                        }
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }



}
