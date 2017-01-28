package tech42.sathish.inventorymanagement.helper;

/**
 * Created by lenovo on 27/1/17.
 * * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.model.Product;

public class ProductHelper {

    private DatabaseReference db;
    private Boolean saved;
    private ArrayList<Product> products=new ArrayList<>();

    /*
       PASS DATABASE REFRENCE
  */
    public ProductHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(Product product)
    {
        if(product==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child(Constant.PRODUCT).push().setValue(product);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    // IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        products.clear();
        Product product = dataSnapshot.getValue(Product.class);
        products.add(product);

    }

    // RETRIEVE
    public ArrayList<Product> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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
        });
        return products;
    }
}
