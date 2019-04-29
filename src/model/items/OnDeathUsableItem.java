package model.items;

import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;

public class OnDeathUsableItem extends Usable {
    private OnDeathTarget target;
    private Spell spell;

    public OnDeathUsableItem(int cost, String name, ItemName itemType, String desc, OnDeathTarget target, Spell spell) {
        super(cost, name, itemType, desc);
        this.target = target;
        this.spell = spell;
    }

    @Override
    public void castItem(Player player) {
        switch (target) {
            case A_FRIENDLY_POWER:
                player.giveSpellToRandomPower(spell, false);
                break;
        }
    }
}
