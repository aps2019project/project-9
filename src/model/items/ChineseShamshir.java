package model.items;


import model.cards.Minion;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

public class ChineseShamshir extends Collectible {
    public ChineseShamshir(String name, ItemName itemName, String desc){
        super(name,itemName,desc);
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
