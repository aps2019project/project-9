package model.cellaffects;

import model.cards.Minion;
import model.enumerations.CellAffectName;

public class FireCellAffect extends CellAffect {

    public FireCellAffect(int turnsActive) {
        super(CellAffectName.FIRE, turnsActive);
    }

    @Override
    public void castCellAffect(Minion minion) {
        minion.reduceHP(2);
        // this method should be called by every move in playground
    }

}
