package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.cards.Spell;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class OnDeathSpecialPower extends SpecialPower {
    private int attackPower;
    private OnDeathTargetType targetType;

    public OnDeathSpecialPower(int attackPower, OnDeathTargetType targetType) {
        super(SpecialPowerActivationTime.ON_DEATH);
        this.attackPower = attackPower;
        this.targetType = targetType;
    }

    public OnDeathSpecialPower(Spell spell){
        super(SpecialPowerActivationTime.ON_DEATH);
        this.spell = spell;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // minion current cell ( last cell it was alive )
        if (spell == null) {
            switch (targetType) {
                case ENEMY_HERO:
                    minion.getPlayer().getOpponent().getHero().reduceHP(attackPower);
                    break;
                case EIGHT_AROUND_MINIONS:
                    for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                        if (aroundCell.hasCardOnIt())
                            aroundCell.getMinionOnIt().reduceHP(2);
                    }
                    break;
            }
        }else{
            spell.castSpell(getSpellCastCell(spell.getTargetType()));
        }
    }
}
