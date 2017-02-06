package tech42.sathish.inventorymanagement.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;

public class LabSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_computer, img_chemistry, img_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_selection);

        img_computer = (ImageView)findViewById(R.id.computer);
        img_chemistry = (ImageView) findViewById(R.id.chemistry);
        img_store = (ImageView) findViewById(R.id.store);

        img_chemistry.setOnClickListener(this);
        img_computer.setOnClickListener(this);
        img_store.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(img_chemistry))
        {
            Constant.STORAGE = "chemistry";
        }
        else if (v.equals(img_computer))
        {
            Constant.STORAGE = "computer";
        }
        else if (v.equals(img_store))
        {
            Constant.STORAGE = "store";
        }

        Intent gotoHome = new Intent(LabSelectionActivity.this, HomeActivity.class);
        startActivity(gotoHome);
    }
}
