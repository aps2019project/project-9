package model.items;


import model.cards.Minion;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

public class ChineseShamshir extends Collectible {
    public ChineseShamshir(){
        target = ItemTarget.MELEE;
    }
    @Override
    public void useItem() {
        for(Minion playerMinion : owningPlayer.getMinionsInPlayGround()){
            if(playerMinion.getAttackType() == MinionAttackType.MELEE){
                playerMinion.addAP(5);
            }
        }
    }
}
