package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class PassiveSpecialPower extends SpecialPower {
    private ArrayList<Buff> buffs;
    private PassiveTargetType targetType;

    public PassiveSpecialPower(ArrayList<Buff> buffs, PassiveTargetType targetType) {
        super(SpecialPowerActivationTime.PASSIVE);
        this.buffs = buffs;
        this.targetType = targetType;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // cell is minion current cell
        switch (targetType) {
            case CURRENT_CELL:
                for (Buff buff : buffs) {
                    buff.startBuff(cell);
                }
                break;
            case CURRENT_AND_EIGHT_FRIENDLY_AROUND:
                //specialPowerSpell.castSpell(cell);
                for (Buff buff : buffs) {
                    buff.startBuff(cell);
                }
                for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                    if (aroundCell.hasCardOnIt()
                            && minion.getPlayer().getMinionsInPlayGround().contains(aroundCell.getMinionOnIt()))
                        //specialPowerSpell.castSpell(aroundCell);
                        for (Buff buff : buffs) {
                            buff.startBuff(aroundCell);
                        }
                }
                break;
            case ALL_FRIENDLY_MINIONS:
                for (Minion minion1 : minion.getPlayer().getMinionsInPlayGround()) {
                    for (Buff buff : buffs) {
                        buff.startBuff(minion1.getCell());
                    }
                }
                break;
        }
    }
}
