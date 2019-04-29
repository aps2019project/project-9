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
        // while a minion enters this cell ( cell that has this CellAffect )
        minion.setReductionOfOthersAttack(1);
        HollyBuff hollyBuff = new HollyBuff(1,false,false,false);  //be checked
        hollyBuff.startBuff(minion.getCell());
    }


}
