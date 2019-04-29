package model.cards;

import com.google.gson.annotations.Expose;
import model.Cell;
import model.enumerations.CardType;
import model.enumerations.HeroName;
import model.enumerations.MinionAttackType;

import java.util.ArrayList;

public class Hero extends Minion {
    // hero does not have minion name ( not use the minion name that inherit )
    private static ArrayList<Hero> heroes = new ArrayList<>();
    @Expose
    private int coolDown;
    private int turnsRemained; // for cool down
    @Expose
    private Spell heroSpell;
    @Expose
    private HeroTargetType spellTargetType; // for spell
    @Expose
    private HeroName heroName;
    private boolean isSpecialPowerActivated = false;

    public Hero(HeroName heroName, int cost, int HP, int AP, MinionAttackType attackType, int attackRange,
                Spell heroSpell, int MP, int coolDown, int cardID, String name, String desc, boolean isFars,
                HeroTargetType spellTargetType) {
        super(name, cost, MP, HP, AP, attackType, attackRange, null, CardType.MINION,cardID,desc,
                null, isFars);    //not complete
        this.coolDown = coolDown;
        this.heroSpell = heroSpell;
        this.spellTargetType = spellTargetType;
        this.heroName = heroName;
    }

    public boolean isSpellReady() {
        return turnsRemained == 0;
    }

    public void useSpecialPower(Cell cell) { // cast spell
        turnsRemained = coolDown;
        if(spellTargetType == HeroTargetType.ON_ATTACK)
            isSpecialPowerActivated = true;
        else if (spellTargetType == HeroTargetType.ITSELF)
            heroSpell.castSpell(getCell());
        else if(spellTargetType == HeroTargetType.ALL_POWERS_IN_ROW)
            heroSpell.castSpell(getCell());
        else
            heroSpell.castSpell(cell);
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

    public void setTurnsRemainedForNextTurn(){// every turn ( in nextTurn() )
        if(turnsRemained != 0){
            turnsRemained--;
        }
    }

    public HeroName getHeroName() {
        return heroName;
    }

    public boolean isSpecialPowerActivated() {
        return isSpecialPowerActivated;
    }

    public void setHeroSpell(Spell heroSpell) {
        this.heroSpell = heroSpell;
    }
}
