package model.cards;

import model.*;
import model.enumerations.SpellName;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;

public class Spell {
    private ArrayList<Buff> buffs;
    private SpellTargetType targetType;
    private static ArrayList<Spell> spells;
    private CellAffect cellAffect;
    private static ArrayList<Spell> heroSpells;
    private static ArrayList<Spell> itemSpells;
    private SpellName spellName;
    public void castSpell(Cell cell){}
    public boolean isValidTarget(Cell cell , PlayGround playGround){return false;}
    public static void removeBuffs(Player player , boolean isForPassiveBuffs){}
    public static void attackToEnemy(Card card , int power){}
    public static void sacrifice(Card card){}
    public static void killEnemyMinion(){}
    public ArrayList<Cell> cellsToCast(){return null;}
    public static void dispel(Cell cell){}
    public String toString(){return null;}
}
