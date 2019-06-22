package model.specialPower;

import model.Cell;
import model.buffs.Buff;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class PassiveSpecialPower extends SpecialPower {
    private ArrayList<Buff> buffs;
    private PassiveTargetType targetType;

    public PassiveSpecialPower(ArrayList<Buff> buffs, PassiveTargetType targetType) {
        super(SpecialPowerActivationTime.PASSIVE);
        this.buffs = buffs;
        for (Buff buff : buffs) {
            buff.setTurnsActive(buff.getTurnsRemained() / 2);
            buff.setTurnsRemained(buff.getTurnsRemained() / 2);
        }
        this.targetType = targetType;
    }

    public PassiveSpecialPower(Spell spell){
        super(SpecialPowerActivationTime.PASSIVE);
        this.spell = spell;
    }

    @Override
    public void castSpecialPower(Cell cell) {
        // cell is minion current cell

        if (spell == null) {
            switch (targetType) {
                case CURRENT_CELL:
                    for (Buff buff : buffs) {
                        buff.getCopy().startBuff(cell);
                    }
                    break;
                case CURRENT_AND_EIGHT_FRIENDLY_AROUND:
                    //specialPowerSpell.castSpell(cell);
                    for (Buff buff : buffs) {
                        buff.getCopy().startBuff(cell);
                    }
                    for (Cell aroundCell : cell.getPlayGround().getAroundCells(cell)) {
                        if (aroundCell.hasCardOnIt()
                                && (minion.getPlayer().getMinionsInPlayGround().contains(aroundCell.getMinionOnIt())))
                            for (Buff buff : buffs) {
                                buff.getCopy().startBuff(aroundCell);
                            }
                    }
                    break;
                case ALL_FRIENDLY_MINIONS:
                    for (Minion minion1 : minion.getPlayer().getMinionsInPlayGround()) {
                        for (Buff buff : buffs) {
                            buff.getCopy().startBuff(minion1.getCell());
                        }
                    }
                    break;
            }
        }else {
            spell.castSpell(getSpellCastCell(spell.getTargetType(), minion));
        }
    }
}
