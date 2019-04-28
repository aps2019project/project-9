package model.items;

import model.Player;
import model.enumerations.ItemName;

public class ManaItems extends Usable {
    public ManaItems(String name, ItemName itemName, String desc){
        super(name,itemName,desc);
    }

    @Override
    public void castItem(Player player) {
        if (itemType == ItemName.TAJ_DANAYEE) {
            if (player.getBattle().getTurn() <= 3)
                player.addMana(1);
            else
                player.deleteUsableItem();
        }else if(itemType == ItemName.KING_WISDOM){
            player.addMana(1);
        }
    }
}
