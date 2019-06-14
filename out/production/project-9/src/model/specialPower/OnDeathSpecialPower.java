package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class OnDeathSpecialPower extends SpecialPower {
    private int attackPower;
    private OnDeathTargetType targetType;
    private ArrayList<Buff> buffs = null;

    public OnDeathSpecialPower(int attackPower, OnDeathTargetType targetType) {
        super(SpecialPowerActivationTime.ON_DEATH);
        this.attackPower = attackPower;
        this.targetType = targetType;
    }

    public OnDeathSpecialPower(ArrayList<Buff> buffs, OnDeathTargetType targetType) {
        super(SpecialPowerActivationTime.ON_DEATH);
        this.targetType = targetType;
        this.buffs = buffs;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // minion current cell ( last cell it was alive )
        if (buffs != null){
            //TODO
        }else {
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
        }
    }
}
