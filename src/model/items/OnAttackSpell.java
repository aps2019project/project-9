package model.items;

import model.Player;
import model.cards.Spell;

public class OnAttackSpell extends Usable{
    private Spell spell;
    private boolean isAssignedToTarget;
    private boolean isSpellForOpponent;

    @Override
    public void castItem(Player player) {

    }
}
