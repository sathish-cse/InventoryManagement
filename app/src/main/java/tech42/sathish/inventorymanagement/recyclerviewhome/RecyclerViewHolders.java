package tech42.sathish.inventorymanagement.recyclerviewhome;

/**
 * Created by lenovo on 25/1/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tech42.sathish.inventorymanagement.activity.DashboardActivity;
import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.activity.ExportActivity;
import tech42.sathish.inventorymanagement.activity.ExportTransactionsActivity;
import tech42.sathish.inventorymanagement.activity.ImportActivity;
import tech42.sathish.inventorymanagement.activity.ImportTransactionsActivity;
import tech42.sathish.inventorymanagement.activity.ProductsActivity;

class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView menuName;
    ImageView menuImage;
    private Intent gotoNextActivity;

    RecyclerViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        menuName = (TextView)itemView.findViewById(R.id.tv_name);
        menuImage = (ImageView)itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();
        if ( getPosition() == 0 )
            gotoNextActivity = new Intent(context, ImportActivity.class);

        if ( getPosition() == 1 )
            gotoNextActivity = new Intent(context, ExportActivity.class);

        else if ( getPosition() == 2)
            gotoNextActivity = new Intent(context, ProductsActivity.class);

        else if ( getPosition() == 3)
            gotoNextActivity = new Intent(context, ImportTransactionsActivity.class);

        else if ( getPosition() == 4)
            gotoNextActivity = new Intent(context, ExportTransactionsActivity.class);

        else if ( getPosition() == 5)
            gotoNextActivity = new Intent(context, DashboardActivity.class);

        context.startActivity(gotoNextActivity);
    }
}
