package model.items;

import model.Cell;
import model.cards.Minion;
import model.enumerations.ItemName;

public class NefrinMarg extends Collectible {
    private Minion ownedMinion;

    NefrinMarg(String name, ItemName itemName, String desc) {
        super(name, itemName, desc);
    }

    @Override
    public void useItem() {
        ownedMinion = owningPlayer.getRandomPower(false);
        ownedMinion.addActiveItem(this);
    }

    public void castItem() {
        // on killed for the minion has it
        ownedMinion.getNearRandomOpponentPower().reduceHP(8);
        ownedMinion.deleteActiveItem(this);
    }

}
