package model.cards;

import model.*;
import model.buffs.Buff;
import model.cellaffects.CellAffect;
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
    public void castSpell(Cell cell){

    }
    public boolean isValidTarget(Cell cell , PlayGround playGround){
        switch (targetType){
            case A_CARD:
                return cell.hasCardOnIt();
            case ENEMY_HERO:
            case AN_ENEMY_CARD:
            case FRIENDLY_HERO:
            case A_FRIENDLY_CARD:
            case ALL_ENEMY_CARDS:
            case AN_ENEMY_MINION:
            case TWO_IN_TWO_SQUARE:
            case A_FRIENDLY_MINIOIN:
            case ALL_FRIENDLY_CARDS:
            case ALL_ENEMY_IN_COLUMN:
            case THREE_IN_THREE_SQUARE:
            case AN_ENEMY_MINION_IN_EIGHT_HERO:
        }

    }
    public static void removeBuffs(Player player , boolean isForPassiveBuffs){}
    public static void attackToEnemy(Card card , int power){}
    public static void sacrifice(Card card){}
    public static void killEnemyMinion(){}
    public ArrayList<Cell> cellsToCast(){return null;}
    public static void dispel(Cell cell){}
    public String toString(){return null;}
}
