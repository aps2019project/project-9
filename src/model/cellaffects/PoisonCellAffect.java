package model.cellaffects;

import model.cards.Minion;

public class PoisonCellAffect extends CellAffect{
    public PoisonCellAffect(){
        turnsActive = 3;
    }
    @Override
    public void castCellAffect(Minion minion) {
        minion.reduceHP(1);
    }

    @Override
    public void expireCellAffect() {
        affectedCell.getMinionOnIt().addHP(1);
    }
}
