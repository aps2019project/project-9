package model.items;

import model.Cell;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public class ManaCollectible extends Collectible {


    public ManaCollectible(int cost, String name, ItemName itemType, String desc) {
        super(cost, name, itemType, desc);
    }


    @Override
    public void useItem() {
        owningPlayer.setManaForNextTurnIncrease(3);
    }
}
