package model.cellaffects;

import model.cards.Minion;

public class PoisonCellAffect extends CellAffect{
    @Override
    public void castCellAffect(Minion minion) {
        minion.reduceHP(1);
    }

    @Override
    public void expireCellAffect() {
        affectedCell.getMinionOnIt().addHP(1);
    }
}
