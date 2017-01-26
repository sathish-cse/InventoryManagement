package tech42.sathish.inventorymanagement.recyclerview;

/**
 * Created by lenovo on 25/1/17.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tech42.sathish.inventorymanagement.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView menuName;
    public ImageView menuImage;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        menuName = (TextView)itemView.findViewById(R.id.tv_name);
        menuImage = (ImageView)itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position" + getPosition(), Toast.LENGTH_SHORT).show();

    }
}
