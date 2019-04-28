package model.specialPower;

import model.Cell;
import model.cards.Spell;
import model.enumerations.MinionName;
import model.enumerations.SpecialPowerActivationTime;

public class OnAttackSpecialPower extends SpecialPower {
    private Spell onAttackSpell;
    private boolean isAntiHolly; // only for DARANDE_SHIR it should be true
    private boolean isDispel; // only for GHOOL_DOSAR

    public OnAttackSpecialPower(Spell onAttackSpell, boolean isAntiHolly, boolean isDispel) {
        // for DARANDE_SHIR and GHOOL_DOSAR there should be no spell ( null )
        super(SpecialPowerActivationTime.ON_ATTACK);
        this.isAntiHolly = isAntiHolly;
        this.isDispel = isDispel;
        this.onAttackSpell = onAttackSpell;
    }


    @Override
    public void castSpecialPower(Cell cell) {
        // cell is the target cell that the minion wants to attack() to

        if (minion.getMinionName() == MinionName.DOSAR_GHOOL)
            cell.getMinionOnIt().dispelPositiveBuffs();
        else if (onAttackSpell != null)
            onAttackSpell.castSpell(cell);
    }

    public boolean isAntiHolly() {
        return isAntiHolly;
    }

    public boolean isDispel() {
        return isDispel;
    }
}

