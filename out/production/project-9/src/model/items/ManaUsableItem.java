package model.items;

import model.Player;
import model.enumerations.ItemName;

public class ManaUsableItem extends Usable {

    public ManaUsableItem(int cost, String name, ItemName itemType, String desc) {
        super(cost, name, itemType, desc);
    }

    @Override
    public void castItem(Player player) {
        if (itemType == ItemName.TAJ_DANAYEE) {
            if (player.getBattle().getTurn() <= 3) {
                player.addMana(1);
            }
        }else if(itemType == ItemName.KING_WISDOM){
            player.addMana(1);
        }
    }
}
