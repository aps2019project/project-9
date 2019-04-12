package model.items;

import model.Player;

public class TajeDanayee extends Usable {
    public TajeDanayee(){
        cost = 300;
    }
    @Override
    public void castItem(Player player) {
        if (player.getBattle().getTurn() <= 3)
            player.addMana(1);
        else
            player.deleteUsableItem();
    }
}
