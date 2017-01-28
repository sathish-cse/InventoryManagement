package tech42.sathish.inventorymanagement.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.helper.ProductHelper;
import tech42.sathish.inventorymanagement.model.Product;

import static tech42.sathish.inventorymanagement.constant.Constant.quantity_Unit_Array;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class ExportActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference databaseReference;
    ProductHelper firebaseHelper;
    private EditText edittext_item,edittext_quantity,edittext_price,editText_buyer;
    private Button button_export;
    private MaterialBetterSpinner unit_materialDesignSpinner;
    private String string_quantity,string_buyer,string_unit,string_item,string_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        initializeFirebaseDatabase();
        findViews();
    }

    private void initializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new ProductHelper(databaseReference);
    }

    private void findViews()
    {
        edittext_item = (EditText)findViewById(R.id.item);
        edittext_quantity = (EditText)findViewById(R.id.qty);
        edittext_price = (EditText)findViewById(R.id.price);
        editText_buyer = (EditText)findViewById(R.id.buyer);
        button_export = (Button)findViewById(R.id.btn_import);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, quantity_Unit_Array);
        unit_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.unit);
        unit_materialDesignSpinner.setAdapter(arrayAdapter);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Lato-Light.ttf");
        unit_materialDesignSpinner.setTypeface(tf);

        button_export.setOnClickListener( this );
        
    }

    @Override
    public void onClick(View v) {

        if ( v == button_export)
        {
            getData();
            getItemDetails();
            setData();
        }
    }

    private void getData()
    {
        string_item = edittext_item.getText().toString().toUpperCase();
        string_price = edittext_price.getText().toString();
        string_quantity = edittext_quantity.getText().toString();
        string_buyer = editText_buyer.getText().toString();
        string_unit = unit_materialDesignSpinner.getText().toString();
    }

    private void setData()
    {
        Product product = new Product();
        if(editTextValidation())
        {
            product.setItem( string_item );
            product.setPrice( string_price );
            product.setQuantity( string_quantity );
            product.setBuyer( string_buyer );
            product.setUnit( string_unit );
            product.setDate( getCurrentDate() );
        }
        else
            Toast.makeText(getApplicationContext(),"Details must not be empty..",Toast.LENGTH_SHORT).show();

        if(firebaseHelper.update(product) && firebaseHelper.addExportTransaction(product)) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Product Exported successfully..", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Product didn't export..",Toast.LENGTH_SHORT).show();

    }

    private boolean editTextValidation()
    {
        if ( string_item.isEmpty() || string_quantity.isEmpty() || string_price.isEmpty() || string_buyer.isEmpty() || string_unit.isEmpty())
            return false;
        else
            return true;
    }

    private void clearEditText()
    {
        edittext_quantity.setText("");
        edittext_item.setText("");
        editText_buyer.setText("");
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


    private void getItemDetails()
    {
        DatabaseReference getChildListener = databaseReference.child(Constant.PRODUCT).child(string_item.toUpperCase());
        getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Product product = dataSnapshot.getValue(Product.class);
                editText_buyer.setText(product.getBuyer());
                edittext_price.setText(product.getPrice());
                edittext_quantity.setText(product.getQuantity());
                unit_materialDesignSpinner.setText(product.getUnit());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}