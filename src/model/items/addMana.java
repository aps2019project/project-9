package model.items;

import model.Cell;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public class addMana extends Collectible {


    public addMana(int cost, String name, ItemName itemType, int itemID, String desc) {
        super(cost, name, itemType, itemID, desc);
    }



    @Override
    public void useItem() {
        owningPlayer.setUsedAddManaItem(true);
    }
}
