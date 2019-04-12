package model.items;

import model.enumerations.ItemName;

import java.util.ArrayList;

public class Item {
    private static ArrayList<Item> items = new ArrayList<>();
    private int cost;
    private String name;
    private ItemName itemType;
    private String itemID;

    public String toString() {
        return null;
    }
}
