package model.items;

import model.Cell;
import model.Player;
import model.buffs.Buff;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.MinionAttackType;
import model.enumerations.SpellTargetType;
import model.items.itemEnumerations.SpellItemTarget;

import java.util.ArrayList;

public class SpellUsableItem extends Usable { // simply just casts a spell ( arrays of buffs )
    private ArrayList<Buff> buffs;
    private SpellItemTarget target;

    public SpellUsableItem(int cost, String name, ItemName itemType,
                           String desc, ArrayList<Buff> buffs, SpellItemTarget target) {
        super(cost, name, itemType, desc);
        this.buffs = buffs;
        this.target = target;
    }

    @Override
    public void castItem(Player player) {
        if (target == SpellItemTarget.FRINEDLY_HERO) {
            Cell cell = player.getHero().getCell();
            for (Buff buff : buffs) {
                buff.getCopy().startBuff(cell);
            }
            player.deleteUsableItem();
        } else if (target == SpellItemTarget.RANDOM_ENEMY_POWER) {
            Spell spell = new Spell(null, 0, 0, SpellTargetType.A_POWER, 0, "", buffs
                    , null, null);
            player.giveSpellToRandomPower(spell, false);
            player.deleteUsableItem();
        } else if (target == SpellItemTarget.ENEMY_HERO_RANGED_OR_HYBRID) {
            if (player.getOpponent().getHero().getAttackType() == MinionAttackType.HYBRID
                    || player.getOpponent().getHero().getAttackType() == MinionAttackType.RANGED) {
                for (Buff buff : buffs) {
                    buff.getCopy().startBuff(player.getOpponent().getHero().getCell());
                }
                player.deleteUsableItem();
            }
        }
    }
}
