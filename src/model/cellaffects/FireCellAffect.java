package model.cellaffects;

import model.cards.Minion;

public class FireCellAffect extends CellAffect{
    @Override
    public void castCellAffect(Minion minion) {
        minion.reduceHP(2);
        // this method should be called in the nextTurn method in battle.
    }

    @Override
    public void expireCellAffect() {
        // nothing
    }
}
