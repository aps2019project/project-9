package model.cards;


import com.google.gson.annotations.Expose;
import model.DefaultCards;
import model.Player;
import model.buffs.Buff;
import model.cellaffects.CellAffect;
import model.enumerations.CardType;
import model.enumerations.HeroName;
import model.enumerations.MinionName;
import model.enumerations.SpellName;
import model.specialPower.SpecialPower;

import java.util.ArrayList;

public class Card {

    @Expose
    protected int cost;
    @Expose
    protected int MP;
    @Expose
    protected CardType cardType;
    @Expose
    protected int cardID;
    protected String battleID;
    @Expose
    protected String name;
    @Expose
    protected String desc;

    public Card(int cost, int MP, CardType cardType,
                int cardID, String name, String desc) {
        this.cost = cost;
        this.MP = MP;
        this.cardType = cardType;
        this.cardID = cardID;
        this.name = name;
        this.desc = desc;
    }


    public CardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCost() {
        return cost;
    }

    public String getDesc() {
        return desc;
    }

    public String getBattleID() {
        return battleID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setBattleID(Player player) {
        int id = 1;
        for (Minion minion : player.getMinionsInPlayGround()) {
            try {
                if (minion.getMinionName().equals(((Minion) this).getMinionName()))
                    id++;
            } catch (NullPointerException e) {

            }
        }
        battleID = player.getName() + "_"
                + this.getName()
                + "_" + id;
    }

    public void setBattleID(String id) {
        this.battleID = id;
    }

    public int getMP() {
        return MP;
    }

    public Card getCustomCopy() {
        if (this instanceof Spell) {
            CellAffect cellAffect = null;
            if (((Spell) this).getCellAffect() != null)
                cellAffect = ((Spell) this).getCellAffect().getCopy();
            ArrayList<Buff> buffs = null;
            if (((Spell) this).getBuffs() != null && ((Spell) this).getBuffs().size() > 0) {
                buffs = new ArrayList<>();
                for (Buff buff : ((Spell) this).getBuffs()) {
                    buffs.add(buff.getCopy());
                }
            }
            return new Spell(this.name, cost, MP, ((Spell) this).getTargetType(), cardID, desc,
                    buffs, cellAffect, SpellName.CUSTOM);
        } else if (this instanceof Hero) {
            Spell spell = ((Spell) ((Hero) this).getSpell().getCustomCopy());
            return new Hero(spell, HeroName.CUSTOM, cost, ((Hero) this).HP, ((Hero) this).AP, ((Hero) this).attackType,
                    ((Hero) this).attackRange, MP, ((Hero) this).getCoolDown(), cardID, name, desc, false);
        } else if (this instanceof Minion) {
            Minion current = ((Minion) this);
            SpecialPower specialPower = null;
            if (current.specialPower != null)
                specialPower = current.specialPower.getCustomCopy();
            return new Minion(name, cost, MP, current.HP, current.AP, current.attackType, current.attackRange,
                    specialPower, CardType.MINION, cardID, desc, MinionName.CUSTOM, false);
        }
        return null;
    } // get copy of custom card

    public Card getNormalCopy() {
        if (this instanceof Spell) {
            return DefaultCards.getSpell(((Spell) this).getSpellName());
        } else if (this instanceof Hero) {
            return DefaultCards.getHero(((Hero) this).getHeroName());
        } else {
            return DefaultCards.getMinion(((Minion) this).getMinionName());
        }
    }

    public boolean isCustom() {
        if (this instanceof Spell && ((Spell) this).getSpellName() == SpellName.CUSTOM)
            return true;
        if (this instanceof Hero && ((Hero) this).getHeroName() == HeroName.CUSTOM)
            return true;
        if (this instanceof Minion && ((Minion) this).getMinionName() == MinionName.CUSTOM)
            return true;
        return false;
    }
}
