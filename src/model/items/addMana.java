package model.items;

import model.Cell;
import model.enumerations.ItemName;

public class addMana extends Collectible {
    public boolean isUsed = false;

    @Override
    public void useItem() {
        if (itemType == ItemName.MAJOON_MANA)
            isUsed = true;
    }
}
