package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class BuffSpecialPower extends SpecialPower {
    private ArrayList<Buff> buffs;

    public BuffSpecialPower(SpecialPowerActivationTime activationTime){
        this.specialPowerActivationTime = activationTime;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // current minion cell
        // if EAGLE , cell is Minion
        for (Buff buff : buffs) {
            buff.startBuff(cell);
        }
        // for SHIR DARANDE
        if(cell.getMinionOnIt().hasActiveHollyBuff()){
            cell.getMinionOnIt().reduceHP(1);
        }
        // for GHOOL SNAKE

    }
}
