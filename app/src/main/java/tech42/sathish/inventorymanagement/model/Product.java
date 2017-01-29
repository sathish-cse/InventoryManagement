package tech42.sathish.inventorymanagement.model;

/**
 * Created by lenovo on 27/1/17.
 */

public class Product {
    public String seller,buyer;
    public String quantity;
    public String productid;
    public String price;
    public String unit;
    public String date;
    public String item;

    public Product()
    {}

    public String getSeller()
    {
        return seller;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setSeller(String seller)
    {
        this.seller = seller;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public void setProductid(String productid)
    {
        this.productid = productid;
    }

    public String getPrice()
    {
        return price;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getDate()
    {
        return date;
    }

    public String getItem()
    {
        return item;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setItem(String item)
    {
        this.item = item;
    }

    public String getBuyer()
    {
        return buyer;
    }

    public void setBuyer(String buyer)
    {
        this.buyer = buyer;
    }

}

