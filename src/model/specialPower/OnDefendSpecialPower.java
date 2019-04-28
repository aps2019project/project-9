package model.specialPower;

import model.Cell;
import model.enumerations.BuffName;
import model.enumerations.SpecialPowerActivationTime;

public class OnDefendSpecialPower extends SpecialPower {
    private OnDefendType onDefendType;
    private BuffName deactivatedBuff; // the buff that should be deactivated on target minion
    /*public OnDefendSpecialPower(OnDefendType onDefendType ,BuffName deactivatedBuff){
        this.onDefendType = onDefendType;
        if(onDefendType == OnDefendType.BUFF)
            this.deactivatedBuff = deactivatedBuff;
        specialPowerActivationTime = SpecialPowerActivationTime.ON_DEFEND;
    }*/

    public OnDefendSpecialPower(OnDefendType onDefendType, BuffName deactivatedBuff) {
        super(SpecialPowerActivationTime.ON_DEFEND);
        this.onDefendType = onDefendType;
        this.deactivatedBuff = deactivatedBuff;
    }

    public BuffName getDeactivatedBuff(){
        return deactivatedBuff;
    }
    public OnDefendType getOnDefendType(){
        return onDefendType;
    }
    @Override
    public void castSpecialPower(Cell cell) {
        // the buff not cast on it
    }
}


