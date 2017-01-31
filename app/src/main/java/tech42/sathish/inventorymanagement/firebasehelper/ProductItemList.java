package tech42.sathish.inventorymanagement.firebasehelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.activity.HomeActivity;
import tech42.sathish.inventorymanagement.activity.LoginActivity;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.customadapter.ProductCustomAdapter;
import tech42.sathish.inventorymanagement.model.Product;

/**
 * Created by lenovo on 31/1/17.
 */

public class ProductItemList {

    private String mCurrentUserUid;
    private List<String> itemList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mProductDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ProductCustomAdapter ProductCustomAdapter;


    private void initializeAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeUsersDatabase() {
        mProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.USERMAIL).child(Constant.PRODUCT);
    }

    private void initializeUsersKeyList() {
        itemList = new ArrayList<String>();
    }


    private void setUserData(FirebaseUser user) {
        mCurrentUserUid = user.getUid();
    }

    private void queryAllUsers() {
        mChildEventListener = getChildEventListener();
        mProductDatabaseReference.addChildEventListener(mChildEventListener);
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
                        itemList.add(userUid);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();
                    if(!userUid.equals(mCurrentUserUid)) {

                        Product user = dataSnapshot.getValue(Product.class);

                        int index = itemList.indexOf(userUid);
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
