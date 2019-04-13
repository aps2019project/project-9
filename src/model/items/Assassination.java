package model.items;

import model.Player;

public class Assassination extends Usable{
    public Assassination(){
        this.cost = 15000;
    }
    @Override
    public void castItem(Player player) {
        // does no thing in here
        player.getOpponent().getHero().reduceHP(1);
    }
}
