package tech42.sathish.inventorymanagement.activity;

import android.app.Dialog;
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

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.firebasehelper.ProductStorageHelper;
import tech42.sathish.inventorymanagement.model.Product;

import static tech42.sathish.inventorymanagement.constant.Constant.quantity_Unit_Array;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class ExportActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private ProductStorageHelper firebaseHelper;
    private EditText edittext_item, edittext_quantity, edittext_price, editText_buyer;
    private Button button_export;
    private MaterialBetterSpinner unit_materialDesignSpinner;
    private String string_quantity, string_buyer, string_unit, string_item, string_price, import_price, import_date, import_seller;
    private Integer import_qty, export_qty, export_children_count;
    private ArrayList<String> itemList = new ArrayList<>();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        initializeFirebaseDatabase();
        findViews();
        getItemCount();
        showSearchDialog();

    }

    private void initializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new ProductStorageHelper(databaseReference);
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
    public final void onClick(View v) {

        getData();

        if ( v. equals( button_export ) )
        {
            if(editTextValidation()) {
                if(quantityValidation())
                {setData();}
                else {
                    Toast.makeText(getApplicationContext(), "Exported quantity " + string_quantity + " is more than stock quantity : " + import_qty, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Details must not be empty..",Toast.LENGTH_SHORT).show();}
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
        // Update Import Array
        Product product = new Product();
            product.setItem( string_item );
            product.setPrice( import_price );
            product.setQuantity( export_qty.toString() );
            product.setSeller( import_seller );
            product.setUnit( string_unit );
            product.setDate( import_date );

        // Add Export Array
        Product arrayProduct = new Product();
            arrayProduct.setItem( string_item );
            arrayProduct.setPrice( string_price );
            arrayProduct.setQuantity( string_quantity );
            arrayProduct.setBuyer( string_buyer );
            arrayProduct.setUnit( string_unit );
            arrayProduct.setDate( getCurrentDate() );

        if(firebaseHelper.update(product) && firebaseHelper.addExportTransaction(export_children_count,arrayProduct)) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Product Exported successfully..", Toast.LENGTH_SHORT).show();
            refresh();
        }
        else{
            Toast.makeText(getApplicationContext(),"Product didn't export..",Toast.LENGTH_SHORT).show();}

    }

    private boolean editTextValidation()
    {
        return !(string_item.isEmpty() || string_quantity.isEmpty() || string_price.isEmpty() || string_buyer.isEmpty() || string_unit.isEmpty());
    }

    private void clearEditText()
    {
        edittext_quantity.setText(Constant.EMPTY);
        edittext_item.setText(Constant.EMPTY);
        editText_buyer.setText(Constant.EMPTY);
        edittext_price.setText(Constant.EMPTY);
        unit_materialDesignSpinner.clearFocus();
        unit_materialDesignSpinner.setText(Constant.EMPTY);
    }

    private String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);

        return df.format(c.getTime());
    }


    private void getItemDetails()
    {
            DatabaseReference getChildListener = databaseReference.child(HomeActivity.USERMAIL).child(Constant.PRODUCT).child(string_item.toUpperCase());
            getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        Product product = dataSnapshot.getValue(Product.class);
                        editText_buyer.setText(product.getBuyer());
                        edittext_price.setText(product.getPrice());
                        import_price = product.getPrice();
                        edittext_quantity.setText(product.getQuantity());
                        import_qty = Integer.parseInt(product.getQuantity());
                        unit_materialDesignSpinner.setText(product.getUnit());
                        import_date = product.getDate();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Item Not Found..",Toast.LENGTH_SHORT).show();
                        showSearchDialog();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
    }

    private void getItemCount()
    {
        try {
            DatabaseReference getChildListener = databaseReference.child(HomeActivity.USERMAIL).child(Constant.EXPORT_TRANSACTIONS);
            getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    export_children_count = (int) dataSnapshot.getChildrenCount() ;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            export_children_count = 0;
        }
    }

    private Boolean quantityValidation()
    {
        if((Integer.parseInt(string_quantity) < (import_qty))) {
            export_qty = import_qty - (Integer.parseInt(string_quantity));
            return true;
        }
        else{
            return false;}

    }

    public final void refresh()
    {
        Intent refresh = getIntent();
        finish();
        startActivity(refresh);
    }

    private void showSearchDialog()
    {
        final Dialog dialog = new Dialog(ExportActivity.this);

        //setting custom layout to dialog
        dialog.setContentView(R.layout.layout_export_item_search);
        dialog.setTitle(Constant.ITEMSEARCH);
        dialog.setCanceledOnTouchOutside(false);

        final AutoCompleteTextView editextSearch;

        editextSearch = (AutoCompleteTextView) dialog.findViewById(R.id.item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,getItemList());
        editextSearch.setAdapter(adapter);
        editextSearch.setThreshold(0);

        //adding button click event
        Button search = (Button) dialog.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editextSearch.getText().toString().isEmpty()) {
                        edittext_item.setText(editextSearch.getText().toString());
                        getData();
                        getItemDetails();
                        dialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Item must not empty..",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        finish();
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
