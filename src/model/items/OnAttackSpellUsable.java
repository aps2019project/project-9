package model.items;

import model.Cell;
import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.MinionAttackType;
import model.items.itemEnumerations.OnAttackOwningMinionType;
import model.items.itemEnumerations.OnAttackTargetType;

public class OnAttackSpellUsable extends Usable { // KAMAN_DAMOOL , TERROR_HOOD , POISONOUS_DAGGER , SHOCK_HAMMER
    private Spell spell;
    private OnAttackTargetType targetType;
    private OnAttackOwningMinionType minionType;

    public OnAttackSpellUsable(int cost, String name, ItemName itemType, int itemID, String desc,
                               Spell spell, OnAttackTargetType targetType, OnAttackOwningMinionType minionType) {
        super(cost, name, itemType, itemID, desc);
        this.spell = spell;
        this.targetType = targetType;
        this.minionType = minionType;
    }

    @Override
    public void castItem(Player player) {
        switch (minionType) {
            case RANGED_HYBRID_HERO:
                if (player.getHero().getAttackType() == MinionAttackType.RANGED || player.getHero().getAttackType()
                        == MinionAttackType.HYBRID) {
                    player.getHero().setOnAttackItem(this);
                    player.deleteUsableItem();
                }
                break;
            case ALL_FRIENDLY_POWERS:
                player.setOnAttackItemForAllPlayers(this);
                player.deleteUsableItem();
                break;
            case FRIENDLY_HERO:
                player.getHero().setOnAttackItem(this);
                player.deleteUsableItem();
                break;
        }
    }

    public void doOnAttack(Cell cell) { // should be called in attack()
        // start spell of this item on the opponent
        // cell that attacks
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
