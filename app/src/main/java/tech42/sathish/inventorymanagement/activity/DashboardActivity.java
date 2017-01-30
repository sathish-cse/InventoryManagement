package tech42.sathish.inventorymanagement.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.item.DashboardItemObject;
import tech42.sathish.inventorymanagement.recyclerviewdashboard.DashboardAdapter;


public class DashboardActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeRecyclerViews();
    }

    private void initializeRecyclerViews()
    {
        List<DashboardItemObject> rowListItem = getAllItem();
        gridLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        DashboardAdapter rcAdapter = new DashboardAdapter(DashboardActivity.this,rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<DashboardItemObject> getAllItem(){

        List<DashboardItemObject> allItems = new ArrayList<DashboardItemObject>();
        allItems.add(new DashboardItemObject(Constant.IMPORTQTY, "1078"));
        allItems.add(new DashboardItemObject(Constant.EXPORTQTY, "1060"));
        allItems.add(new DashboardItemObject(Constant.IMPORT_TRANSACTIONS ,"15049"));
        allItems.add(new DashboardItemObject(Constant.EXPORT_TRANSACTIONS, "57648"));
        return allItems;
    }
}
