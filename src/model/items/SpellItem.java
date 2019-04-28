package model.items;

import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;

public class SpellItem extends Usable{
    private Spell spell;

    SpellItem(String name, ItemName itemName, String desc){
        super(name,itemName,desc);
    }
    @Override
    public void castItem(Player player) {
        if(itemType == ItemName.NAMOOS_SEPAR){
            spell.castSpell(player.getHero().getCell());
            player.deleteUsableItem();
        }else if (itemType == ItemName.SOUL_EATER){
            player.giveSpellToRandomPower(spell , false);
        }
    }
}
