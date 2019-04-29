package model.items;

import model.Player;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public abstract class Usable extends Item{

    public Usable(int cost, String name, ItemName itemType, int itemID, String desc) {
        super(cost, name, itemType, itemID, desc);
    }

    public abstract void castItem(Player player);

}
