package model.items;

import model.Cell;
import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.MinionAttackType;

public class OnAttackSpell extends Usable {
    private Spell spell;
    private boolean isAssignedToTarget = false;
    private boolean isSpellForOpponent = false;

    public OnAttackSpell(String name, ItemName itemName, String desc) {
        super(name,itemName,desc);
        switch (itemName) {
            case TERROR_HOOD:
                isSpellForOpponent = true;
                // spell = weakness buff with power 2 for one turn
            case POISONOUS_DAGGER:
                isSpellForOpponent = true;
                // spell = poison buff for one turn
            case SHOCK_HAMMER:
                isSpellForOpponent = true;
                // spell ...
        }
    }

    @Override
    public void castItem(Player player) {
        if (isSpellForOpponent) {
            switch (itemType) {
                case KAMAN_DAMOOL:
                    if (player.getHero().getAttackType() == MinionAttackType.RANGED || player.getHero().getAttackType()
                            == MinionAttackType.HYBRID) {
                        player.getHero().setOnAttackItem(this);
                        player.deleteUsableItem();
                    }
                case TERROR_HOOD:
                    player.giveSpellToRandomPower(spell , true);
                case POISONOUS_DAGGER:
                    player.giveSpellToRandomPower(spell , true);
                case SHOCK_HAMMER:
                    player.getHero().setOnAttackItem(this);
                    player.deleteUsableItem();
            }
        } else {

        }
    }

    public void doOnAttack(Cell cell) {
        // start spell of this item on the opponent
        if (itemType == ItemName.KAMAN_DAMOOL || itemType == ItemName.SHOCK_HAMMER)
            spell.castSpell(cell);

    }
}
