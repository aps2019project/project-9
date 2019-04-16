package model.specialPower;

import model.Cell;

public class OnDeathRevenge extends SpecialPower {
    // GHOOL TAK CHESHM
    @Override
    public void castSpecialPower(Cell cell) {
        // minion current cell
        // GHOOL TAK SHAKH
        for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
            if(aroundCell.hasCardOnIt()){
                aroundCell.getMinionOnIt().reduceHP(2);
            }
        }
        //
    }
}
