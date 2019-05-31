package model.cellaffects;

import model.cards.Minion;
import model.enumerations.CellAffectName;

public class PoisonCellAffect extends CellAffect{

    public PoisonCellAffect(int turnsActive) {
        super(CellAffectName.POISON, turnsActive);
    }

    @Override
    public void castCellAffect(Minion minion) {
        minion.reduceHP(1);
    }


}
