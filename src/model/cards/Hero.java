package model.cards;

import model.Cell;
import model.enumerations.CardType;
import model.enumerations.HeroName;
import model.enumerations.MinionAttackType;
import model.enumerations.MinionName;
import model.specialPower.SpecialPower;

import java.util.ArrayList;

public class Hero extends Minion {
    // hero does not have minion name ( not use the minion name that inherit )
    private static ArrayList<Hero> heroes;
    private int cooldown;
    private Spell heroSpell;
    private HeroTargetType spellTargetType; // for spell
    private HeroName heroName;

    public Hero(int cost, int MP, int cardID, String name, String desc, MinionAttackType attackType, int HP, int AP,
                int attackRange, boolean isFars, int cooldown, Spell heroSpell,
                HeroTargetType spellTargetType, HeroName heroName) {
        super(cost, MP, CardType.MINION, name,cardID, desc, null, attackType, HP, AP, attackRange, null, isFars);
        this.cooldown = cooldown;
        this.heroSpell = heroSpell;
        this.spellTargetType = spellTargetType;
        this.heroName = heroName;
    }

    public Hero copy() {
        return new Hero(cost, MP, cardID, name, desc, attackType, HP, AP, attackRange
                , isFars, cooldown, heroSpell, spellTargetType, heroName);
    }

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
        String string = "Name : " + getName() + " - AP : " + getAP() + " - HP : " + getHP() + " - Class : "
                + getAttackType() + " - Special power: " + getDesc();
        return string;
    }
}
