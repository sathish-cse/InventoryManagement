package tech42.sathish.inventorymanagement.recyclerview;

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
import tech42.sathish.inventorymanagement.activity.ImportActivity;
import tech42.sathish.inventorymanagement.activity.ProductsActivity;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView menuName;
    public ImageView menuImage;
    private Integer position;
    private Intent gotoNextActivity;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        menuName = (TextView)itemView.findViewById(R.id.tv_name);
        menuImage = (ImageView)itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position" + getPosition(), Toast.LENGTH_SHORT).show();
        position = getPosition();

        Context context = view.getContext();
        if ( position == 0 )
            gotoNextActivity = new Intent(context, ImportActivity.class);

        else if ( position == 4)
            gotoNextActivity = new Intent(context, ProductsActivity.class);

        context.startActivity(gotoNextActivity);
    }
}
