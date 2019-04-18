package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.enumerations.SpecialPowerActivationTime;

public class OnSpawnSpecialPower extends SpecialPower {
    private Buff castingBuff;
    private OnSpawnTargetCell targetCell;

    public OnSpawnSpecialPower(OnSpawnTargetCell targetCell, Buff castingBuff) {
        this.targetCell = targetCell;
        this.castingBuff = castingBuff;
        this.specialPowerActivationTime = SpecialPowerActivationTime.ON_SPAWN;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // cell is minion current cell in here ( the cell that the minion is Spawn On First )
        switch (targetCell) {
            case EIGHT_AROUND:
                for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                    if (aroundCell.hasCardOnIt() &&
                            aroundCell.getMinionOnIt().getPlayer().equals(minion.getPlayer().getOpponent())) {
                        castingBuff.startBuff(aroundCell);
                    }
                }
                break;
            case TWO_DISTANCE_CELLS:
                for (Cell twoDistanceCell : cell.getPlayGround().getTwoDistanceCells(cell)) {
                    if(twoDistanceCell.hasCardOnIt() &&
                    twoDistanceCell.getMinionOnIt().getPlayer().equals(minion.getPlayer().getOpponent()))
                        castingBuff.startBuff(twoDistanceCell);
                }
                break;
            case A_RANDOM_ENEMY_MINION:
                // or can be a buff insteadOF reduce HP
                minion.getPlayer().getOpponent().getRandomPower(false).reduceHP(16);
                break;
        }
    }
}
