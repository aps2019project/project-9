package model.items;

import model.Cell;
import model.buffs.Buff;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

import java.util.ArrayList;

public class SpellCollectible extends Collectible {
    private ArrayList<Buff> buffs;
    protected ItemTarget target;


    public SpellCollectible(int cost, String name, ItemName itemType,
                            String desc, ArrayList<Buff> buffs, ItemTarget target) {
        super(cost, name, itemType, desc);
        this.buffs = buffs;
        this.target = target;
    }

    @Override
    public void useItem() {
        if(target == ItemTarget.RANDOM_FRIENDLY_CARD)
            for (Buff buff : buffs) {
                buff.getCopy().startBuff(owningPlayer.getRandomPower(false).getCell());
            }
        else if(target == ItemTarget.RANDOM_RANGED_OR_HYBRID)
            for (Buff buff : buffs) {
                buff.getCopy().startBuff(owningPlayer.getRandomPower(true).getCell());
            }
        else if(target == ItemTarget.MELEE){
            for (Minion minion : owningPlayer.getMinionsInPlayGround()) {
                if(minion.getAttackType() == MinionAttackType.MELEE){
                    minion.addAP(5);
                }
            }
        }
    }
}
