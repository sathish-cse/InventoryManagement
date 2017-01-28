package tech42.sathish.inventorymanagement.customadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.model.Product;

/**
 * Created by lenovo on 27/1/17.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.ViewHolderProducts> {
    
  
    private Context context;
    private List<Product> products;
    
    public ProductCustomAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderProducts(context, LayoutInflater.from(parent.getContext()).inflate(R.layout.products_card_view, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolderProducts holder, int position) {

        Product product = products.get(position);

        // Set item
        holder.getItem().setText(product.getItem());

        // Set location
        holder.getLocation().setText(product.getQuantity() + product.getUnit());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void refill(Product product) {
        products.add(product);
        notifyDataSetChanged();
    }

    public void changeUser(int index, Product product) {
        products.set(index,product);
        notifyDataSetChanged();
    }

    public void clear() {
        products.clear();
    }


    /* ViewHolder for RecyclerView */
    public class ViewHolderProducts extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_item;
        private TextView textView_location;
        private Context mContextViewHolder;

        public ViewHolderProducts(Context context, View itemView) {
            super(itemView);
            textView_item = (TextView)itemView.findViewById(R.id.nameTxt);
            textView_location = (TextView)itemView.findViewById(R.id.locationTxt);
            mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

        public TextView getItem() {
            return textView_item;
        }
        public TextView getLocation() {
            return textView_location;
        }


        @Override
        public void onClick(View view) {

           /* User user = mUsers.get(getLayoutPosition());

            String chatRef = user.createUniqueChatRef(mCurrentUserCreatedAt,mCurrentUserEmail);

            Intent chatIntent = new Intent(mContextViewHolder, ChatActivity.class);
            chatIntent.putExtra(ExtraIntent.EXTRA_CURRENT_USER_ID, mCurrentUserId);
            chatIntent.putExtra(ExtraIntent.EXTRA_RECIPIENT_ID, user.getRecipientId());
            chatIntent.putExtra(ExtraIntent.EXTRA_CHAT_REF, chatRef);

            // Start new activity
            mContextViewHolder.startActivity(chatIntent);*/

        }
    }

}