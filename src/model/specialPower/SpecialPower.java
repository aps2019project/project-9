package model.specialPower;

import model.Cell;
import model.cards.Minion;
import model.enumerations.SpecialPowerActivationTime;

public abstract class SpecialPower { // FARS_PAHLAVAN remaining

    public SpecialPower(SpecialPowerActivationTime specialPowerActivationTime) {
        this.specialPowerActivationTime = specialPowerActivationTime;
    }

    protected SpecialPowerActivationTime specialPowerActivationTime;
    protected Minion minion;
    public SpecialPowerActivationTime getSpecialPowerActivationTime(){return specialPowerActivationTime;}
    public abstract void castSpecialPower(Cell cell);

    public void setMinion(Minion minion) {
        this.minion = minion;
    }
}
