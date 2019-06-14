package model.specialPower;

import com.google.gson.annotations.Expose;
import model.Cell;
import model.PlayGround;
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

    public Spell getSpell() {
        return spell;
    }

    public SpecialPower getCustomCopy(){
        switch (specialPowerActivationTime){
            case ON_DEATH:
                return new OnDeathSpecialPower(((Spell) spell.getCustomCopy()));
            case COMBO:
                return new ComboSpecialPower();
            case ON_DEFEND:
                return this;
            case ON_ATTACK:
                return new OnAttackSpecialPower(((Spell) spell.getCustomCopy()));
            case ON_SPAWN:
                return new OnSpawnSpecialPower(((Spell) spell.getCustomCopy()));
            case PASSIVE:
                return new PassiveSpecialPower(((Spell) spell.getCustomCopy()));
        }
        return null;
    }

    public static Cell getSpellCastCell(SpellTargetType targetType, Minion minion){
        PlayGround playGround = minion.getPlayer().getBattle().getPlayGround();
        switch (targetType){
            case A_POWER:
                return playGround.getRandomPowerCell(minion.getPlayer());
            case ENEMY_HERO:
                return minion.getPlayer().getOpponent().getHero().getCell();
            case ALL_ENEMY_IN_COLUMN:
                return playGround.getRandomPowerCell(minion.getPlayer().getOpponent());
            case A_CELL:
                return playGround.getRandomPowerCell(minion.getPlayer());
            case ALL_ENEMY_IN_ROW:
                return playGround.getRandomPowerCell(minion.getPlayer().getOpponent());
            case AN_ENEMY_MINION_IN_EIGHT_HERO:
                return minion.getCell();
            case THREE_IN_THREE_SQUARE:
                return playGround.getRandomCell();
            case TWO_IN_TWO_SQUARE:
                return playGround.getRandomCell();
            case FRIENDLY_HERO:
                return minion.getPlayer().getHero().getCell();
            case A_FRIENDLY_MINION:
                return playGround.getRandomPowerCell(minion.getPlayer());
            case ALL_FRIENDLY_POWERS:
                return minion.getCell();
            case AN_ENEMY_MINION:
                return playGround.getRandomPowerCell(minion.getPlayer().getOpponent());
            case AN_ENEMY_POWER:
                return playGround.getRandomPowerCell(minion.getPlayer().getOpponent());
            case ALL_ENEMY_POWERS:
                return minion.getPlayer().getOpponent().getHero().getCell();
            case IT_SELF:
                return minion.getCell();
            case A_FRIENDLY_POWER:
                return playGround.getRandomPowerCell(minion.getPlayer());
        }
        return null;
    }
}
