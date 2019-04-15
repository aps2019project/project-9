package model.cellaffects;


import model.cards.Minion;

public class HollyCellAffect extends CellAffect {

    @Override
    public void castCellAffect(Minion minion) {
        // while a minion enters this cell ( cell that has this CellAffect )
        minion.setReductionOfOthersAttack(1);
    }

    @Override
    public void expireCellAffect() {
        affectedCell.getMinionOnIt().setReductionOfOthersAttack(0);
    }

}
