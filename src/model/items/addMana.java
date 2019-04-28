package model.items;

import model.Cell;
import model.enumerations.ItemName;

public class addMana extends Collectible {
    public boolean isUsed = false;

    addMana(String name, ItemName itemName, String desc) {
        super(name, itemName, desc);
    }

    @Override
    public void useItem() {
        if (itemType == ItemName.MAJOON_MANA)
            isUsed = true;
    }
}
