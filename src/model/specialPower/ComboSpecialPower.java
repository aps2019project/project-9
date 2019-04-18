package model.specialPower;

import model.Cell;
import model.enumerations.SpecialPowerActivationTime;

public class ComboSpecialPower  extends SpecialPower{
    public ComboSpecialPower(){
        specialPowerActivationTime = SpecialPowerActivationTime.COMBO;
    }
    @Override
    public void castSpecialPower(Cell cell) {

    }
}
