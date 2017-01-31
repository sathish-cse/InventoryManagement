package tech42.sathish.inventorymanagement.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.firebasehelper.ProductStorageHelper;
import tech42.sathish.inventorymanagement.model.Product;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class ImportActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private ProductStorageHelper firebaseHelper;
    private EditText edittext_quantity,edittext_price,editText_seller;
    private Button button_import;
    private MaterialBetterSpinner unit_materialDesignSpinner;
    private String string_quantity,string_seller,string_unit,string_item,string_price;
    private Integer import_qty,import_price,import_children_count;
    private ArrayList<String> itemList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteItemName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        initializeFirebaseDatabase();
        findViews();
        getItemCount();

    }

    private void initializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new ProductStorageHelper(databaseReference);
    }

    private void findViews()
    {
        autoCompleteItemName = (AutoCompleteTextView) findViewById(R.id.item);
        edittext_quantity = (EditText)findViewById(R.id.qty);
        edittext_price = (EditText)findViewById(R.id.price);
        editText_seller = (EditText)findViewById(R.id.seller);
        button_import = (Button)findViewById(R.id.btn_import);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Constant.quantity_Unit_Array);
        unit_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.unit);
        unit_materialDesignSpinner.setAdapter(arrayAdapter);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Lato-Light.ttf");
        unit_materialDesignSpinner.setTypeface(tf);

        button_import.setOnClickListener( this );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,getItemList());
        autoCompleteItemName.setAdapter(adapter);
        autoCompleteItemName.setThreshold(0);
    }

    @Override
    public void onClick(View v) {

        getData();

        if ( v == button_import)
        {
            if(editTextValidation()) {
                getItemDetails();
            }
            else
                Toast.makeText(getApplicationContext(),"Details must not be empty..",Toast.LENGTH_SHORT).show();
        }
    }

    private void getData()
    {
        string_item = autoCompleteItemName.getText().toString().toUpperCase();
        string_price = edittext_price.getText().toString();
        string_quantity = edittext_quantity.getText().toString();
        string_seller = editText_seller.getText().toString();
        string_unit = unit_materialDesignSpinner.getText().toString();
    }

    private void setData()
    {
        // UPDATE PR0DUCT ARRAY
        Product product = new Product();
            product.setItem( string_item );
            product.setPrice( import_price.toString() );
            product.setQuantity( import_qty.toString() );
            product.setSeller( string_seller );
            product.setUnit( string_unit );
            product.setDate( getCurrentDate() );

        // ADD IMPORT TRANSACTION
        Product Product = new Product();
        Product.setItem( string_item );
        Product.setPrice( string_price );
        Product.setQuantity( string_quantity );
        Product.setSeller( string_seller );
        Product.setUnit( string_unit );
        Product.setDate( getCurrentDate() );

        if(firebaseHelper.save(product) && firebaseHelper.addImportTransaction(import_children_count,Product)) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Product saved successfully..", Toast.LENGTH_SHORT).show();
            refresh();
        }
        else
            Toast.makeText(getApplicationContext(),"Product didn't save..",Toast.LENGTH_SHORT).show();

    }

    private boolean editTextValidation()
    {
        return !(string_item.isEmpty() || string_quantity.isEmpty() || string_price.isEmpty() || string_seller.isEmpty() || string_unit.isEmpty());
    }

    private void clearEditText()
    {
        edittext_quantity.setText("");
        autoCompleteItemName.setText("");
        editText_seller.setText("");
        edittext_price.setText("");
        unit_materialDesignSpinner.clearFocus();
        unit_materialDesignSpinner.setText("");
    }

    private String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);

        return df.format(c.getTime());
    }

    private void getItemCount()
    {
        try {
            DatabaseReference getChildListener = databaseReference.child(HomeActivity.USERMAIL).child(Constant.IMPORT_TRANSACTIONS);
            getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    import_children_count = (int) dataSnapshot.getChildrenCount() ;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            import_children_count = 0;
        }
    }

    public void refresh()
    {
        Intent refresh = getIntent();
        finish();
        startActivity(refresh);
    }

    private void getItemDetails()
    {
            DatabaseReference getChildListener = databaseReference.child(HomeActivity.USERMAIL).child(Constant.PRODUCT).child(string_item.toUpperCase());
            getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if( dataSnapshot.exists()) {
                        Product product = dataSnapshot.getValue(Product.class);
                        import_qty = Integer.parseInt(string_quantity) + Integer.parseInt(product.getQuantity());
                        import_price = Integer.parseInt(string_price) + Integer.parseInt(product.getPrice());
                        setData();
                    }
                    else
                    {
                        import_qty = Integer.parseInt(string_quantity);
                        import_price = Integer.parseInt(string_price);
                        setData();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    private ArrayList<String> getItemList()
    {
        DatabaseReference getItemListener = databaseReference.child(HomeActivity.USERMAIL).child(Constant.PRODUCT);
        getItemListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    itemList.add(String.valueOf(dsp.getKey())); //add result into array list
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return itemList;
    }
}