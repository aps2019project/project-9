package model.items;

import model.Player;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

public class ParSimorgh extends Usable {
    public ParSimorgh() {
        cost = 3500;
        target = ItemTarget.RANGED_OR_HYBRID;
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
