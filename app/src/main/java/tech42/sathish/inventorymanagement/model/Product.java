package tech42.sathish.inventorymanagement.model;

/**
 * Created by lenovo on 27/1/17.
 */

public class Product {
    private String item,location;
    private int quantity;

    public Product()
    {}

    public String getItem()
    {
        return item;
    }

    public String getLocation()
    {
        return location;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setItem(String item)
    {
        this.item = item;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}

