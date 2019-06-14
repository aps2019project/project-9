package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.cards.Spell;
import model.enumerations.MinionName;
import model.enumerations.SpecialPowerActivationTime;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;
import java.util.Random;

public class OnAttackSpecialPower extends SpecialPower {
    private ArrayList<Buff> buffs;
    private boolean isAntiHolly; // only for DARANDE_SHIR it should be true
    private boolean isDispel; // only for GHOOL_DOSAR


    public OnAttackSpecialPower(ArrayList<Buff> buffs, boolean isAntiHolly, boolean isDispel) {
        // for DARANDE_SHIR and GHOOL_DOSAR there should be no spell ( null )
        super(SpecialPowerActivationTime.ON_ATTACK);
        this.isAntiHolly = isAntiHolly;
        this.isDispel = isDispel;
        this.buffs = buffs;
    }

    public OnAttackSpecialPower(Spell spell) {
        super(SpecialPowerActivationTime.ON_ATTACK);
        this.spell = spell;
    }


    @Override
    public void castSpecialPower(Cell cell) {
        // cell is the target cell that the minion wants to attack() to
        if (spell == null) {
            if (minion.getMinionName() == MinionName.DOSAR_GHOOL)
                cell.getMinionOnIt().dispelPositiveBuffs();
            else if (buffs != null && cell.hasCardOnIt()) {
                for (Buff buff : buffs) {
                    buff.getCopy().startBuff(cell);
                }
            }
        } else {
            spell.castSpell(getSpellCastCell(spell.getTargetType()));
        }
    }

    public boolean isAntiHolly() {
        return isAntiHolly;
    }

    public boolean isDispel() {
        return isDispel;
    }
}

