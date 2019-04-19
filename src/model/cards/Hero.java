package model.cards;

import model.Cell;

import java.util.ArrayList;

public class Hero extends Minion {
    public Hero(HeroTargetType heroTargetType) {
        this.spellTargetType = heroTargetType;
        this.isHero = true;
    }

    private static ArrayList<Hero> heroes;
    private int cooldown;
    private Spell heroSpell;
    private HeroTargetType spellTargetType;

    public boolean isSpellReady() {
        return false;
    }

    public void castSpell(Cell cell) {
    }

    public void useSpecialPower(Cell cell) {
    }

    public HeroTargetType getSpellTargetType() {
        return spellTargetType;
    }

    public String toString() {
        return null;
    }
}
