package model.items;

import model.Cell;
import model.cards.Spell;
import model.enumerations.ItemName;

public class SpellCollectible extends Collectible {
    private Spell spell;

    SpellCollectible(String name, ItemName itemName, String desc){
        super(name,itemName,desc);
    }
    @Override
    public void useItem() {
        spell.castSpell(owningPlayer.getRandomPower(false).getCell());
    }
}
