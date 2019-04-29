package model.items;

import model.Player;
import model.enumerations.ItemName;

public class Assassination extends Usable {
    public Assassination(int cost, String name, String desc) {
        super(cost, name, ItemName.ASSASINATION_DAGGER, desc);
    }

    @Override
    public void castItem(Player player) {
        // does no thing in here
        player.getOpponent().getHero().reduceHP(1);
    }
}
