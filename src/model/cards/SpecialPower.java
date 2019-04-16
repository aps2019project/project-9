package model.cards;

import model.Cell;
import model.enumerations.SpecialPowerActivationTime;

public abstract class SpecialPower {
    protected SpecialPowerActivationTime specialPowerActivationTime;
    protected Minion minion;

    public abstract void castSpecilaPower(Cell cell);
}
