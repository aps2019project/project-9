package model.items;

import model.Player;
import model.enumerations.ItemName;

public class Ghosl extends Usable {

    public Ghosl(int cost, String name, ItemName itemType, String desc) {
        super(cost, name, itemType, desc);
    }

    @Override
    public void castItem(Player player) {
        // **
    }
}
