package tech42.sathish.inventorymanagement.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.customadapter.ProductCustomAdapter;
import tech42.sathish.inventorymanagement.helper.FirebaseHelper;
import tech42.sathish.inventorymanagement.model.Product;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class ImportActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference databaseReference;
    FirebaseHelper firebaseHelper;
    ProductCustomAdapter productCustomAdapter;
    private EditText edittext_item,edittext_quantity,edittext_location;
    private Button button_import;
    private String string_item,string_location;
    private Integer int_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        intializeFirebaseDatabase();
        findViews();
    }

    private void intializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new FirebaseHelper(databaseReference);
    }

    private void findViews()
    {
        edittext_item = (EditText)findViewById(R.id.item);
        edittext_quantity = (EditText)findViewById(R.id.qty);
        edittext_location = (EditText)findViewById(R.id.location);
        button_import = (Button)findViewById(R.id.btn_import);

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
        string_item = edittext_item.getText().toString();
        string_location = edittext_location.getText().toString();
        int_quantity = Integer.parseInt(edittext_quantity.getText().toString());
    }

    private void setData()
    {
       // if(editTextValidation())
        //{
            Product product = new Product();
            product.setItem( string_item );
            product.setLocation( string_location );
            product.setQuantity( int_quantity );
        //}
        //else
          //  Toast.makeText(getApplicationContext(),"Details must not be empty..",Toast.LENGTH_SHORT).show();

        if(firebaseHelper.save(product))
            Toast.makeText(getApplicationContext(),"Product saved successfully..",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"Product didn't save..",Toast.LENGTH_SHORT).show();

    }

    private boolean editTextValidation()
    {
        if ( string_item.isEmpty() || int_quantity.equals(null) || string_location.isEmpty())
            return false;
        else
            return true;
    }
}