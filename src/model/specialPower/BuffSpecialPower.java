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
        // if EAGLE , cell is Minion current Cell
        for (Buff buff : buffs) {
            buff.startBuff(cell);
        }
    }
}
