package model.items;

import model.Player;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public abstract class Usable extends Item{

    public Usable(int cost, String name, ItemName itemType, String desc) {
        super(cost, name, itemType, desc);
    }

    public abstract void castItem(Player player);

}
