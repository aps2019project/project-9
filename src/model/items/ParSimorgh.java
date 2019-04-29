package model.items;

import model.Player;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

public class ParSimorgh extends Usable {

    public ParSimorgh(int cost, String name, int itemID, String desc) {
        super(cost, name, ItemName.PAR_SIMORGH, itemID, desc);
    }

    @Override
    public void castItem(Player player) {
        if (player.getOpponent().getHero().getAttackType() == MinionAttackType.HYBRID
                || player.getOpponent().getHero().getAttackType() == MinionAttackType.RANGED) {
            player.getOpponent().getHero().reduceAP(2);
            player.deleteUsableItem();
        }
    }
}
