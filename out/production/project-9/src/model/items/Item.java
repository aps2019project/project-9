package model.items;

import com.google.gson.annotations.Expose;
import model.enumerations.ItemName;

import java.util.ArrayList;

public class Item {
    private static ArrayList<Item> items = new ArrayList<>();
    @Expose
    protected int cost;
    @Expose
    protected String name;
    @Expose
    protected ItemName itemType;
    protected int itemID;
    @Expose
    protected String desc;

    public Item(int cost, String name, ItemName itemType,String desc) {
        this.cost = cost;
        this.name = name;
        this.itemType = itemType;
        this.desc = desc;
    }

    public String toString() {
        if (this instanceof Collectible)
            return name + "\nDesc: " + desc;
        return "Name : " + name + "\nDesc: " + desc + "\ncost : " + cost;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public int getItemID() {
        return itemID;
    }

    public int getCost() {
        return cost;
    }

    public ItemName getItemType() {
        return itemType;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getDesc() {
        return desc;
    }
}
