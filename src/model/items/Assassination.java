package model.items;

import model.Player;
import model.enumerations.ItemName;

public class Assassination extends Usable {
    public Assassination(String name, ItemName itemName, String desc) {
        super(name,itemName,desc);
        this.cost = 15000;
    }

    @Override
    public void castItem(Player player) {
        // does no thing in here
        player.getOpponent().getHero().reduceHP(1);
    }
}
