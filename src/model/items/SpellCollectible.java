package model.items;

import model.Cell;
import model.cards.Spell;
import model.enumerations.ItemName;

public class SpellCollectible extends Collectible {
    private Spell spell;

    @Override
    public void useItem() {
        spell.castSpell(owningPlayer.getRandomPower(false).getCell());
    }
}
