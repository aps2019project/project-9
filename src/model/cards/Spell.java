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

    public void castSpell(Cell inputCell) {
        // description in Visual Paradigm
    }

    public boolean isValidTarget(Cell inputCell, PlayGround playGround) {
        switch (targetType) {
            case AN_ENEMY_MINION_IN_EIGHT_HERO:
                break;
            case THREE_IN_THREE_SQUARE:
                break;
            case ALL_ENEMY_IN_COLUMN:
                return (owningPlayer.getBattle().getPlayGround().isForEnemyMinion(inputCell, owningPlayer)
                        || owningPlayer.getOpponent().getHero().getCell().equals(inputCell));
            case TWO_IN_TWO_SQUARE:
                break;
            case ENEMY_HERO:
                return owningPlayer.getOpponent().getHero().getCell().equals(inputCell);
            case A_POWER:
                return inputCell.hasCardOnIt();
            case FRIENDLY_HERO:
                return owningPlayer.getHero().getCell().equals(inputCell);
            case AN_ENEMY_POWER:
                return (owningPlayer.getBattle().getPlayGround().isForEnemyMinion(inputCell, owningPlayer)
                        || owningPlayer.getOpponent().getHero().getCell().equals(inputCell));
            case AN_ENEMY_MINION:
                return owningPlayer.getBattle().getPlayGround().isForEnemyMinion(inputCell, owningPlayer);
            case A_FRIENDLY_POWER:
                return owningPlayer.getBattle().getPlayGround().isForFriendlyMinion(inputCell, owningPlayer)
                        || owningPlayer.getHero().getCell().equals(inputCell);
            case ALL_ENEMY_POWERS:
                break;
            case A_FRIENDLY_MINION:
                return owningPlayer.getBattle().getPlayGround().isForFriendlyMinion(inputCell, owningPlayer);
            case ALL_FRIENDLY_POWERS:
                break;
        }
        return true;
    }


    public ArrayList<Cell> cellsToCast(Cell inputCell) {
        ArrayList<Cell> result = new ArrayList<>();
        if (targetType == SpellTargetType.AN_ENEMY_POWER
        || targetType == SpellTargetType.AN_ENEMY_MINION
        || targetType == SpellTargetType.A_FRIENDLY_POWER
        || targetType == SpellTargetType.A_FRIENDLY_MINION
        || targetType == SpellTargetType.FRIENDLY_HERO
        || targetType == SpellTargetType.ENEMY_HERO
        || targetType == SpellTargetType.A_POWER
        )
            result.add(inputCell);
        else if( targetType == SpellTargetType.TWO_IN_TWO_SQUARE){

        }else if( targetType == SpellTargetType.THREE_IN_THREE_SQUARE){

        }else if(targetType == SpellTargetType.ALL_ENEMY_POWERS){
            for (Minion enemyMinion : owningPlayer.getOpponent().getMinionsInPlayGround()) {
                result.add(enemyMinion.getCell());
            }
            result.add(owningPlayer.getOpponent().getHero().getCell());
        }else if(targetType == SpellTargetType.ALL_FRIENDLY_POWERS){
            for (Minion friendlyMinion : owningPlayer.getMinionsInPlayGround()) {
                result.add(friendlyMinion.getCell());
            }
            result.add(owningPlayer.getHero().getCell());
        }else if( targetType == SpellTargetType.ALL_ENEMY_IN_COLUMN){

        }else if(targetType == SpellTargetType.AN_ENEMY_MINION_IN_EIGHT_HERO){
            // random
        }
        return result;
    }


    public String toString() {
        return null;
    }
}
