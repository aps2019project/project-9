package model.items;

import model.PlayGround;
import model.Player;
import model.buffs.Buff;
import model.buffs.HollyBuff;
import model.cards.Minion;
import model.enumerations.ItemName;
import model.items.itemEnumerations.OnSpawnItemTarget;
import model.specialPower.OnSpawnTargetCell;

public class OnSpawnUsableItem extends Usable {
    private OnSpawnItemTarget target;

    public OnSpawnUsableItem(int cost, String name, ItemName itemType, int itemID,
                             String desc, OnSpawnItemTarget target) {
        super(cost, name, itemType, itemID, desc);
        this.target = target;
    }

    @Override
    public void castItem(Player player) {
        // no thing
        // it is for every friendly power
    }

    public void doOnSpawnAction(Player player, Minion minion) {
        if (itemType == ItemName.ASSASINATION_DAGGER) {
            player.getOpponent().getHero().reduceHP(1);
        } else if (itemType == ItemName.GHOSL) {
            HollyBuff buff = new HollyBuff(2, false, false, false);
            buff.startBuff(minion.getCell());
        }
    }
}
