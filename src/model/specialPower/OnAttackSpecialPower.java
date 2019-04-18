package model.specialPower;

import model.Cell;
import model.cards.Spell;
import model.enumerations.MinionName;
import model.enumerations.SpecialPowerActivationTime;

public class OnAttackSpecialPower extends SpecialPower{
    private Spell onAttackSpell;
    public OnAttackSpecialPower(Spell onAttackSpell){
        this.onAttackSpell = onAttackSpell;
        this.specialPowerActivationTime = SpecialPowerActivationTime.ON_ATTACK;
    }
    @Override
    public void castSpecialPower(Cell cell) {
        // cell is the target cell that the minion wants to attack() to
        if(minion.getMinionName() == MinionName.DOSAR_GHOOL)
            cell.getMinionOnIt().dispelPositiveBuffs();
        else
            onAttackSpell.castSpell(cell);
    }
}
