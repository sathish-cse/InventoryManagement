package tech42.sathish.inventorymanagement.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.helper.ProductHelper;
import tech42.sathish.inventorymanagement.model.Product;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class ImportActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference databaseReference;
    ProductHelper firebaseHelper;
    private EditText edittext_item,edittext_quantity,edittext_price,editText_seller;
    String[] quantity_Unit_Array = {"Number","Kg","Gram","Litre","Ml"};
    private Button button_import;
    private MaterialBetterSpinner unit_materialDesignSpinner;
    private String string_quantity,string_seller,string_unit,string_item,string_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

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
        editText_seller = (EditText)findViewById(R.id.seller);
        button_import = (Button)findViewById(R.id.btn_import);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, quantity_Unit_Array);
        unit_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.unit);
        unit_materialDesignSpinner.setAdapter(arrayAdapter);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Lato-Light.ttf");
        unit_materialDesignSpinner.setTypeface(tf);

        button_import.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        if ( v == button_import)
        {
            getData();
            setData();
        }
    }

    private void getData()
    {
        string_item = edittext_item.getText().toString().toUpperCase();
        string_price = edittext_price.getText().toString();
        string_quantity = edittext_quantity.getText().toString();
        string_seller = editText_seller.getText().toString();
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
            product.setSeller( string_seller );
            product.setUnit( string_unit );
            product.setDate( getCurrentDate() );
        }
        else
           Toast.makeText(getApplicationContext(),"Details must not be empty..",Toast.LENGTH_SHORT).show();

        if(firebaseHelper.save(product) && firebaseHelper.addImportTransaction(product)) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Product saved successfully..", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Product didn't save..",Toast.LENGTH_SHORT).show();

    }

    private boolean editTextValidation()
    {
        if ( string_item.isEmpty() || string_quantity.isEmpty() || string_price.isEmpty() || string_seller.isEmpty() || string_unit.isEmpty())
            return false;
        else
            return true;
    }

    private void clearEditText()
    {
        edittext_quantity.setText("");
        edittext_item.setText("");
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
}