package model.items;

import model.Cell;
import model.cards.Minion;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public class OnDeathCollectibleItem extends Collectible { // NEFRIN MARG
    private Minion ownedMinion;

    public OnDeathCollectibleItem(int cost, String name, ItemName itemType, int itemID, String desc) {
        super(cost, name, itemType, itemID, desc);
    }


    @Override
    public void useItem() {
        ownedMinion = owningPlayer.getRandomPower(false);
        ownedMinion.setOnDeathCollectibleItem(this);
    }

    public void castItem() {
        // on killed for the minion has it
        ownedMinion.getNearRandomOpponentPower().reduceHP(8);
        ownedMinion.deleteOnDeathCollectible();
    }

    public void setOwnedMinion(Minion ownedMinion) {
        this.ownedMinion = ownedMinion;
    }
}
