package model.specialPower;

import com.google.gson.annotations.Expose;
import model.Cell;
import model.cards.Minion;
import model.enumerations.SpecialPowerActivationTime;

public abstract class SpecialPower { // FARS_PAHLAVAN remaining

    @Expose
    protected SpecialPowerActivationTime specialPowerActivationTime;
    protected Minion minion;
    public SpecialPowerActivationTime getSpecialPowerActivationTime(){return specialPowerActivationTime;}
    public abstract void castSpecialPower(Cell cell);

    public SpecialPower(SpecialPowerActivationTime specialPowerActivationTime) {
        this.specialPowerActivationTime = specialPowerActivationTime;
    }

    public void setMinion(Minion minion) {
        this.minion = minion;
    }
}
