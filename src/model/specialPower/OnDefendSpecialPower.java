package model.specialPower;

import model.Cell;
import model.enumerations.BuffName;
import model.enumerations.SpecialPowerActivationTime;

public class OnDefendSpecialPower extends SpecialPower {
    private OnDefendType onDefendType;
    private BuffName deactivatedBuff;
    public OnDefendSpecialPower(OnDefendType onDefendType ,BuffName deactivatedBuff){
        this.onDefendType = onDefendType;
        if(onDefendType == OnDefendType.BUFF)
            this.deactivatedBuff = deactivatedBuff;
        specialPowerActivationTime = SpecialPowerActivationTime.ON_DEFEND;
    }
    public BuffName getDeactivatedBuff(){
        return deactivatedBuff;
    }
    public OnDefendType getOnDefendType(){
        return onDefendType;
    }
    @Override
    public void castSpecialPower(Cell cell) {

    }
}


