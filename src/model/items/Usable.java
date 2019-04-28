package model.items;

import model.Player;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public abstract class Usable extends Item{
    protected ItemTarget target;

    public abstract void castItem(Player player);

    Usable(String name, ItemName itemName, String desc){
        super(0,name,itemName,0,desc);
    }
}
