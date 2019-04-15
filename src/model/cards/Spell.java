package model.cards;

import model.*;
import model.buffs.Buff;
import model.cellaffects.CellAffect;
import model.enumerations.SpellName;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;

public class Spell extends Card {
    private ArrayList<Buff> buffs;
    private SpellTargetType targetType;
    private static ArrayList<Spell> spells;
    private CellAffect cellAffect;
    private static ArrayList<Spell> heroSpells;
    private static ArrayList<Spell> itemSpells;
    private SpellName spellName;
    private Player owningPlayer; // the player use it

    public static void removeBuffs(Player player, boolean isForPassiveBuffs) {
    }

    public static void attackToEnemy(Card card, int power) {
    }

    public static void sacrifice(Card card) {
    }

    public static void killEnemyMinion() {
    }

    public static void dispel(Cell cell) {
    }

    public void castSpell(Cell cell) {

    }

    public boolean isValidTarget(Cell cell, PlayGround playGround) {
        switch (targetType) {
            case AN_ENEMY_MINION_IN_EIGHT_HERO:
                break;
            case THREE_IN_THREE_SQUARE:
                break;
            case ALL_ENEMY_IN_COLUMN:
                return (owningPlayer.getBattle().getPlayGround().isForEnemyMinion(cell, owningPlayer)
                        || owningPlayer.getOpponent().getHero().getCell().equals(cell));
            case TWO_IN_TWO_SQUARE:
                break;
            case ENEMY_HERO:
                return owningPlayer.getOpponent().getHero().getCell().equals(cell);
            case A_POWER:
                return cell.hasCardOnIt();
            case FRIENDLY_HERO:
                return owningPlayer.getHero().getCell().equals(cell);
            case AN_ENEMY_POWER:
                return (owningPlayer.getBattle().getPlayGround().isForEnemyMinion(cell, owningPlayer)
                        || owningPlayer.getOpponent().getHero().getCell().equals(cell));
            case AN_ENEMY_MINION:
                return owningPlayer.getBattle().getPlayGround().isForEnemyMinion(cell, owningPlayer);
            case A_FRIENDLY_POWER:
                return owningPlayer.getBattle().getPlayGround().isForFriendlyMinion(cell, owningPlayer)
                        || owningPlayer.getHero().getCell().equals(cell);
            case ALL_ENEMY_POWERS:
                break;
            case A_FRIENDLY_MINION:
                return owningPlayer.getBattle().getPlayGround().isForFriendlyMinion(cell, owningPlayer);
            case ALL_FRIENDLY_POWERS:
                break;
        }
        return true;
    }


    public ArrayList<Cell> cellsToCast() {
        return null;
    }


    public String toString() {
        return null;
    }
}
