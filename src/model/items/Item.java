package model.items;

import model.enumerations.ItemName;

import java.util.ArrayList;

public class Item {
    private static ArrayList<Item> items = new ArrayList<>();
    protected int cost;
    protected String name;
    protected ItemName itemType;
    protected String itemID;

    public String toString() {
        return null;
    }
    public static ArrayList<Item> getItems(){return items;}
}
