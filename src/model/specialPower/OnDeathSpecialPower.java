package model.specialPower;

import model.Cell;
import model.enumerations.SpecialPowerActivationTime;

public class OnDeathSpecialPower extends SpecialPower{
    private int attackPower;
    private OnDeathTargetType targetType;

    public OnDeathSpecialPower(int attackPower, OnDeathTargetType targetType) {
        super(SpecialPowerActivationTime.ON_DEATH);
        this.attackPower = attackPower;
        this.targetType = targetType;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // minion current cell ( last cell it was alive )
        switch (targetType){
            case ENEMY_HERO:
                minion.getPlayer().getOpponent().getHero().reduceHP(attackPower);
                break;
            case EIGHT_AROUND_MINIONS:
                for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                    if(aroundCell.hasCardOnIt())
                        aroundCell.getMinionOnIt().reduceHP(2);
                }
                break;
        }
    }
}
