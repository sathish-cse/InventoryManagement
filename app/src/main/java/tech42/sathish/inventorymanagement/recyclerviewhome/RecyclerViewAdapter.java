package tech42.sathish.inventorymanagement.recyclerviewhome;

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
import tech42.sathish.inventorymanagement.item.HomeItemObject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<HomeItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<HomeItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.menuName.setText(itemList.get(position).getName());
        holder.menuImage.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
