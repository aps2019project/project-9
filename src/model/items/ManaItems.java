package model.items;

import model.Player;
import model.enumerations.ItemName;

public class ManaItems extends Usable {

    public ManaItems(int cost, String name, ItemName itemType, int itemID, String desc) {
        super(cost, name, itemType, itemID, desc);
    }

    @Override
    public void castItem(Player player) {
        if (itemType == ItemName.TAJ_DANAYEE) {
            if (player.getBattle().getTurn() <= 3)
                player.addMana(1);
        }else if(itemType == ItemName.KING_WISDOM){
            player.addMana(1);
        }
    }
}
