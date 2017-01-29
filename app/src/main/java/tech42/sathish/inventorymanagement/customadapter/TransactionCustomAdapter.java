package tech42.sathish.inventorymanagement.customadapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.model.Product;

/**
 * Created by lenovo on 27/1/17.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */

public class TransactionCustomAdapter extends RecyclerView.Adapter<TransactionCustomAdapter.ViewHolderProducts> {


    private Context context;
    private List<Product> products;

    public TransactionCustomAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderProducts(context, LayoutInflater.from(parent.getContext()).inflate(R.layout.import_transaction_card_view, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolderProducts holder, int position) {

        Product product = products.get(position);

        // Set item
        holder.getItem().setText(product.getItem());

        // Set Quantity
        holder.getQuantity().setText(product.getQuantity() + " " + product.getUnit());

        // set Date
        holder.getdate().setText(product.getDate());

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
        private TextView textView_quantity;
        private TextView textView_date;
        
        private Context mContextViewHolder;

        public ViewHolderProducts(Context context, View itemView) {
            super(itemView);
            textView_item = (TextView)itemView.findViewById(R.id.nameTxt);
            textView_quantity = (TextView)itemView.findViewById(R.id.quantityTxt);
            textView_date = (TextView)itemView.findViewById(R.id.dateTxt);
            mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

        public TextView getItem() {
            return textView_item;
        }
        public TextView getQuantity() {
            return textView_quantity;
        }
        public  TextView getdate()
        {
            return  textView_date;
        }


        @Override
        public void onClick(View view) {



            Product product = products.get(getLayoutPosition());

            final Dialog dialog = new Dialog(context);

            //setting custom layout to dialog
            dialog.setContentView(R.layout.product_details_view_dialog);
            dialog.setTitle(Constant.IMPORT_TRANSACTIONS);
            dialog.setCanceledOnTouchOutside(false);

            final EditText edittext_item,edittext_quantity,edittext_price,editText_seller,editText_unit,editText_date;

            edittext_item = (EditText)dialog.findViewById(R.id.item);
            edittext_quantity = (EditText)dialog.findViewById(R.id.qty);
            edittext_price = (EditText)dialog.findViewById(R.id.price);
            editText_seller = (EditText)dialog.findViewById(R.id.seller);
            editText_unit = (EditText)dialog.findViewById(R.id.unit);
            editText_date = (EditText)dialog.findViewById(R.id.date);

            edittext_item.setText(product.getItem());
            edittext_quantity.setText(product.getQuantity());
            edittext_price.setText(product.getPrice());
            editText_seller.setText(product.getSeller());
            editText_unit.setText(product.getUnit());
            editText_date.setText(product.getDate());

            //adding button click event
            Button dismissButton = (Button) dialog.findViewById(R.id.btn_ok);
            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
    }

}
