package model.cellaffects;


import model.buffs.HollyBuff;
import model.cards.Minion;
import model.enumerations.CellAffectName;

public class HollyCellAffect extends CellAffect {

    public HollyCellAffect(int turnsActive) {
        super(CellAffectName.HOLLY, turnsActive);
    }

    @Override
    public void castCellAffect(Minion minion) {
        HollyBuff hollyBuff = new HollyBuff(1,false,false,false);
        hollyBuff.getCopy().startBuff(minion.getCell());
    }


}
