package tech42.sathish.inventorymanagement.recyclerviewdashboard;

/**
 * Created by lenovo on 25/1/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tech42.sathish.inventorymanagement.R;


class DashboardHolder extends RecyclerView.ViewHolder {

     TextView dashboardName;
     TextView dashboardValue;

    DashboardHolder(View itemView) {
        super(itemView);

        dashboardName = (TextView)itemView.findViewById(R.id.tv_name);
        dashboardValue = (TextView)itemView.findViewById(R.id.tv_value);

    }

}
