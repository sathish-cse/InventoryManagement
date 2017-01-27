package tech42.sathish.inventorymanagement.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.model.Product;

/**
 * Created by lenovo on 27/1/17.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */

public class ProductCustomAdapter extends BaseAdapter{
  
    private Context context;
    private ArrayList<Product> products;
    
    public ProductCustomAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    
    @Override
    public int getCount() {
        return products.size();
    }
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_import,parent,false);
        }
        TextView textView_item = (TextView) convertView.findViewById(R.id.item);
        TextView textView_quantity = (TextView) convertView.findViewById(R.id.qty);
        TextView textView_location = (TextView) convertView.findViewById(R.id.location);

        final Product product = (Product) this.getItem(position);
        textView_item.setText( product.getItem() );
        textView_quantity.setText( product.getQuantity() );
        textView_location.setText( product.getLocation() );


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, product.getItem() ,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
