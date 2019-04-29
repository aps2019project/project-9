package model.items;

import model.enumerations.ItemName;

import java.util.ArrayList;

public class Item {
    private static ArrayList<Item> items = new ArrayList<>();

    protected int cost;
    protected String name;
    protected ItemName itemType;
    protected int itemID;
    protected String desc;

    public Item(int cost, String name, ItemName itemType, String desc) {
        this.cost = cost;
        this.name = name;
        this.itemType = itemType;
        this.desc = desc;
    }

    public String toString() {
        return "Name : " + name + " - Desc: " + desc;
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
