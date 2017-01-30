package tech42.sathish.inventorymanagement.item;

/**
 * Created by lenovo on 25/1/17.
 */

public class DashboardItemObject {

    private String name;
    private String value;

    public DashboardItemObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
