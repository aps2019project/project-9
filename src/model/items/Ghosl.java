package model.items;

import model.Player;
import model.enumerations.ItemName;

public class Ghosl extends Usable {
    Ghosl(String name, ItemName itemName, String desc) {
        super(name, itemName, desc);
    }

    @Override
    public void castItem(Player player) {
        // **
    }
}
