package model.items;

import model.Cell;
import model.Player;
import model.buffs.Buff;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;

public class SpellItem extends Usable {
    private ArrayList<Buff> buffs;

    public SpellItem(int cost, String name, ItemName itemType, String desc, ArrayList<Buff> buffs) {
        super(cost, name, itemType, desc);
        this.buffs = buffs;
    }

    @Override
    public void castItem(Player player) {
        if (itemType == ItemName.NAMOOS_SEPAR) {
            /*spell.castSpell(player.getHero().getCell());*/
            Cell cell = player.getHero().getCell();
            for (Buff buff : buffs) {
                buff.startBuff(cell);
            }
            player.deleteUsableItem();
        } else if (itemType == ItemName.SOUL_EATER) {
            Spell spell = new Spell(null, 0, 0, SpellTargetType.A_POWER, 0, "", buffs
                    , null, null);
            player.giveSpellToRandomPower(spell, false);
        }
    }
}
