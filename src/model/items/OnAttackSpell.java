package model.items;

import model.Cell;
import model.PlayGround;
import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.MinionAttackType;

public class OnAttackSpell extends Usable { // KAMAN_DAMOOL , TERROR_HOOD , POISONOUS_DAGGER
    private Spell spell;
    private OnAttackTargetType targetType;

    public OnAttackSpell(int cost, String name, ItemName itemType, int itemID, String desc,
                         Spell spell, OnAttackTargetType targetType) {
        super(cost, name, itemType, itemID, desc);
        this.spell = spell;
        this.targetType = targetType;
    }


    @Override
    public void castItem(Player player) {
        switch (itemType) {
            case KAMAN_DAMOOL:
                if (player.getHero().getAttackType() == MinionAttackType.RANGED || player.getHero().getAttackType()
                        == MinionAttackType.HYBRID) {
                    player.getHero().setOnAttackItem(this);
                    player.deleteUsableItem();
                }
                break;
            case TERROR_HOOD:
                player.setOnAttackItemForAllPlayers(this);
                player.deleteUsableItem();
                break;
            case POISONOUS_DAGGER:
                player.setOnAttackItemForAllPlayers(this);
                player.deleteUsableItem();
                break;
            case SHOCK_HAMMER:
                player.getHero().setOnAttackItem(this);
                player.deleteUsableItem();
                break;
        }
    }

    public void doOnAttack(Cell cell) { // should be called in attack()
        // start spell of this item on the opponent
        switch (targetType){
            case RANDOM_ENEMY:
                cell.getMinionOnIt().getPlayer().giveSpellToRandomPower(spell,true);
                break;
            case OPPONENT_CELL:
                spell.castSpell(cell);
                break;
        }

    }

}
