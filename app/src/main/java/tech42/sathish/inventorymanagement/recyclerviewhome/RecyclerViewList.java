package tech42.sathish.inventorymanagement.recyclerviewhome;

import java.util.ArrayList;
import java.util.List;

import tech42.sathish.inventorymanagement.R;
import tech42.sathish.inventorymanagement.constant.Constant;
import tech42.sathish.inventorymanagement.item.HomeItemObject;

/**
 * Created by lenovo on 30/1/17.
 */

public class RecyclerViewList {

    public static List<HomeItemObject> getAllItem(){

        List<HomeItemObject> allItems = new ArrayList<HomeItemObject>();
        allItems.add(new HomeItemObject(Constant.IMPORT, R.drawable.in));
        allItems.add(new HomeItemObject(Constant.EXPORT, R.drawable.out));
        allItems.add(new HomeItemObject(Constant.PRODUCTS, R.drawable.products));
        allItems.add(new HomeItemObject(Constant.IMPORT_TRANSACTIONS, R.drawable.move));
        allItems.add(new HomeItemObject(Constant.EXPORT_TRANSACTIONS, R.drawable.move));
        allItems.add(new HomeItemObject(Constant.DASHBOARD, R.drawable.report));
        return allItems;
    }
}
