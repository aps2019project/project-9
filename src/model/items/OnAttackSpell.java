package model.items;

import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;

public class OnAttackSpell extends Usable{
    private Spell spell;
    private boolean isAssignedToTarget = false;
    private boolean isSpellForOpponent = false;
    public OnAttackSpell(ItemName itemName){
        switch (itemName){
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
        if(isSpellForOpponent) {
            if(itemType == ItemName.SHOCK_HAMMER){
                player.getHero().setOnAttackSpell(spell);
            }
            player.getOpponent().getRandomPower().setOnAttackSpell(spell);
            player.deleteUsableItem();
        }else{

        }
    }
}
