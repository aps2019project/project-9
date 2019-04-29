package model.items;

import model.Player;
import model.enumerations.ItemName;

public class Ghosl extends Usable {

    public Ghosl(int cost, String name, ItemName itemType, int itemID, String desc) {
        super(cost, name, itemType, itemID, desc);
    }

    @Override
    public void castItem(Player player) {

    }
}
