package model.cards;

import model.Cell;
import model.Player;
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
    private int turnsRemained; // for cool down
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

    public boolean isSpellReady() {
        return false;
    }

    public void useSpecialPower(Cell cell) { // cast spell

    }

    public boolean canCastSpell(){ // cool down
        return turnsRemained==0;
    }

    public HeroTargetType getSpellTargetType() {
        return spellTargetType;
    }

    public String toString() {
        String string = "Name : " + getName() + " - AP : " + getAP() + " - HP : " + getHP() + " - Class : "
                + getAttackType() + " - Special power: " + getDesc();
        return string;
    }

    public Spell getHeroSpell() {
        return heroSpell;
    }
}
