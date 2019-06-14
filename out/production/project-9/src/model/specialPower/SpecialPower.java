package model.specialPower;

import com.google.gson.annotations.Expose;
import model.Cell;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.SpecialPowerActivationTime;
import model.enumerations.SpellTargetType;

public abstract class SpecialPower { // FARS_PAHLAVAN remaining

    protected SpecialPowerActivationTime specialPowerActivationTime;
    protected Minion minion;
    protected Spell spell = null;
    public SpecialPowerActivationTime getSpecialPowerActivationTime(){return specialPowerActivationTime;}
    public abstract void castSpecialPower(Cell cell);

    public SpecialPower(SpecialPowerActivationTime specialPowerActivationTime) {
        this.specialPowerActivationTime = specialPowerActivationTime;
    }

    public void setMinion(Minion minion) {
        this.minion = minion;
    }

    private Cell getSpellCastCell(SpellTargetType targetType){
        switch (targetType){
            case A_POWER:
                return minion.getCell();
            case ENEMY_HERO:
                return minion.getPlayer().getOpponent().getHero().getCell();
            case ALL_ENEMY_IN_COLUMN:
                return minion.getPlayer().getOpponent().getHero().getCell();
            case A_CELL:
                return minion.getCell();

            case AN_ENEMY_MINION_IN_EIGHT_HERO:
            case THREE_IN_THREE_SQUARE:
            case TWO_IN_TWO_SQUARE:
            case FRIENDLY_HERO:
            case A_FRIENDLY_MINION:
            case ALL_FRIENDLY_POWERS:
            case AN_ENEMY_MINION:
            case AN_ENEMY_POWER:
            case ALL_ENEMY_POWERS:
            case IT_SELF:
            case A_FRIENDLY_POWER:
        }
        return null;
    }
}
