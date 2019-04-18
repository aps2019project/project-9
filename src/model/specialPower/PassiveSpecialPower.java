package model.specialPower;

import model.Cell;
import model.cards.Spell;
import model.enumerations.SpecialPowerActivationTime;

public class PassiveSpecialPower extends SpecialPower {
    private Spell specialPowerSpell;
    private PassiveTargetType targetType;

    public PassiveSpecialPower(Spell specialPowerSpell, PassiveTargetType targetType) {
        this.specialPowerSpell = specialPowerSpell;
        this.specialPowerActivationTime = SpecialPowerActivationTime.PASSIVE;
        this.targetType = targetType;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // cell is minion current cell
        switch (targetType) {
            case CURRENT_CELL:
                specialPowerSpell.castSpell(cell);
                break;
            case CURRENT_AND_EIGHT_FRIENDLY_AROUND:
                specialPowerSpell.castSpell(cell);
                for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                    if (aroundCell.hasCardOnIt()
                            && minion.getPlayer().getMinionsInPlayGround().contains(aroundCell.getMinionOnIt()))
                        specialPowerSpell.castSpell(aroundCell);
                }
                break;
        }
    }
}
