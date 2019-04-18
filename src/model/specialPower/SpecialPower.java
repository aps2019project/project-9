package model.specialPower;

import model.Cell;
import model.cards.Minion;
import model.enumerations.SpecialPowerActivationTime;

public abstract class SpecialPower {

    protected SpecialPowerActivationTime specialPowerActivationTime;
    protected Minion minion;
    public SpecialPowerActivationTime getSpecialPowerActivationTime(){return specialPowerActivationTime;}
    public abstract void castSpecialPower(Cell cell);
}
