package tech42.sathish.inventorymanagement.recyclerviewdashboard;

/**
 * Created by lenovo on 25/1/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.item.DashboardItemObject;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardHolder> {

    private List<DashboardItemObject> itemList;
    private Context context;

    public DashboardAdapter(Context context, List<DashboardItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_dashboard, null);
        DashboardHolder dashboardHolder = new DashboardHolder(layoutView);
        return dashboardHolder;
    }

    @Override
    public void onBindViewHolder(DashboardHolder holder, int position) {
        holder.dashboardName.setText(itemList.get(position).getName());
        holder.dashboardValue.setText(itemList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
