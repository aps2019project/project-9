package model.items;

import model.Player;
import model.enumerations.ItemTarget;

public abstract class Usable extends Item{
    private ItemTarget target;

    public abstract void castItem(Player player);
}
