package tech42.sathish.inventorymanagement.item;

/**
 * Created by lenovo on 25/1/17.
 */

public class HomeItemObject {

    private String name;
    private int photo;

    public HomeItemObject(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
