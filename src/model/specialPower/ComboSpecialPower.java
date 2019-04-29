package model.specialPower;

import model.Cell;
import model.enumerations.SpecialPowerActivationTime;

public class ComboSpecialPower  extends SpecialPower{

    public ComboSpecialPower() {
        super(SpecialPowerActivationTime.COMBO);
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // not thing
    }
}
