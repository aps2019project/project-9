package model.cards;

import model.*;
import model.buffs.Buff;
import model.cellaffects.CellAffect;
import model.enumerations.CardType;
import model.enumerations.SpellName;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private static ArrayList<Spell> heroSpells = new ArrayList<>();
    private static ArrayList<Spell> itemSpells = new ArrayList<>();
    private static ArrayList<Spell> specialPowerSpells = new ArrayList<>();
    private ArrayList<Buff> buffs;
    private SpellTargetType targetType;
    private CellAffect cellAffect;
    private SpellName spellName;
    private Player owningPlayer; // the player use it

    public Spell(String name, int cost, int MP, SpellTargetType targetType, int cardID,
                 String desc, ArrayList<Buff> buffs, CellAffect cellAffect, SpellName spellName) {
        super(cost, MP, CardType.SPELL, cardID, name, desc);
        this.buffs = buffs;
        this.targetType = targetType;
        this.cellAffect = cellAffect;
        this.spellName = spellName;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }


    public static void removeBuffs(Player player) {
        // for Spell Num 12
        for (Minion friendlyPower : player.getMinionsInPlayGround()) {
            friendlyPower.dispelNegativeBuffs();
        }
        player.getHero().dispelNegativeBuffs();
        for (Minion enemyPower : player.getOpponent().getMinionsInPlayGround()) {
            enemyPower.dispelPositiveBuffs();
        }
        player.getOpponent().getHero().dispelPositiveBuffs();
    }

    public static void removeBuffs(Player player, ArrayList<Cell> cellsToCast) { // remove negative of friendly , and positive of
        // enemy
        for (Cell cell : cellsToCast) {
            if (cell.hasCardOnIt()) {
                if (player.getMinionsInPlayGround().contains(cell.getMinionOnIt()))
                    cell.getMinionOnIt().dispelNegativeBuffs();
                else
                    cell.getMinionOnIt().dispelPositiveBuffs();
            }
        }
    }

    public static void killEnemyMinion(ArrayList<Cell> targetCells) {
        for (Cell targetCell : targetCells) {
            targetCell.getMinionOnIt().reduceHP(targetCell.getMinionOnIt().getHP());
        }
    }


    public void castSpell(Cell inputCell) {
        // description in Visual Paradigm
        if (isValidTarget(inputCell)) {
            ArrayList<Cell> targetCells = getCellsToCast(inputCell);
            if (buffs == null) {
                // it is cellAffect Spell
                for (Cell targetCell : targetCells) {
                    cellAffect.putCellAffect(targetCell);
                }
            } else if (cellAffect == null) {
                // it is Buff Spell
                for (Buff castingBuff : buffs) {
                    for (Cell targetCell : targetCells) {
                        if (targetCell.hasCardOnIt() && targetCell.getMinionOnIt().canDefend(this, castingBuff)) {
                            castingBuff.startBuff(targetCell);
                        }
                    }
                }
            } else {
                // exception Spells
                if (spellName == SpellName.AREA_DISPEL)
                    removeBuffs(owningPlayer, targetCells);
                else if (spellName == SpellName.DISPEL) // not sure about dispels
                    removeBuffs(owningPlayer);
                else if (spellName == SpellName.KINGS_GUARD)
                    killEnemyMinion(targetCells);
            }
        }
    }

    public boolean isValidTarget(Cell inputCell) { // not completed
        PlayGround playGround = owningPlayer.getBattle().getPlayGround();
        switch (targetType) {
            case AN_ENEMY_MINION_IN_EIGHT_HERO:
                return (playGround.isForEnemyMinion(inputCell, owningPlayer)
                        && playGround.getAroundCells(owningPlayer.getHero().getCell()).contains(inputCell));
            case THREE_IN_THREE_SQUARE:
                break;
            case ALL_ENEMY_IN_COLUMN:
                return (playGround.isForEnemyMinion(inputCell, owningPlayer));
            case TWO_IN_TWO_SQUARE:
                break;
            case ENEMY_HERO:
                return owningPlayer.getOpponent().getHero().getCell().equals(inputCell);
            case A_POWER:
                return inputCell.hasCardOnIt();
            case FRIENDLY_HERO:
                return owningPlayer.getHero().getCell().equals(inputCell);
            case AN_ENEMY_POWER:
                return (playGround.isForEnemyMinion(inputCell, owningPlayer));
            case AN_ENEMY_MINION:
                return playGround.isForEnemyMinion(inputCell, owningPlayer)
                        && !(owningPlayer.getOpponent().getHero().getCell().equals(inputCell));
            case A_FRIENDLY_POWER:
                return playGround.isForFriendlyMinion(inputCell, owningPlayer);
            case ALL_ENEMY_POWERS:
                break;
            case A_FRIENDLY_MINION:
                return playGround.isForFriendlyMinion(inputCell, owningPlayer)
                        && !(owningPlayer.getHero().getCell().equals(inputCell));
            case ALL_FRIENDLY_POWERS:
                break;
            case A_CELL:
                return true;
            case IT_SELF:
                return true;
        }
        return true;
    }


    public ArrayList<Cell> getCellsToCast(Cell inputCell) {
        ArrayList<Cell> result = new ArrayList<>();
        PlayGround playGround = owningPlayer.getBattle().getPlayGround();
        if (targetType == SpellTargetType.AN_ENEMY_POWER
                || targetType == SpellTargetType.AN_ENEMY_MINION
                || targetType == SpellTargetType.A_FRIENDLY_POWER
                || targetType == SpellTargetType.A_FRIENDLY_MINION
                || targetType == SpellTargetType.FRIENDLY_HERO
                || targetType == SpellTargetType.ENEMY_HERO
                || targetType == SpellTargetType.A_POWER
                || targetType == SpellTargetType.IT_SELF
                || targetType == SpellTargetType.AN_ENEMY_MINION_IN_EIGHT_HERO
        )
            result.add(inputCell);
        else if (targetType == SpellTargetType.TWO_IN_TWO_SQUARE) {
            result = playGround.getSquareCells(inputCell, 2);
        } else if (targetType == SpellTargetType.THREE_IN_THREE_SQUARE) {
            result = playGround.getSquareCells(inputCell, 3);
        } else if (targetType == SpellTargetType.ALL_ENEMY_POWERS) {
            for (Minion enemyMinion : owningPlayer.getOpponent().getMinionsInPlayGround()) {
                result.add(enemyMinion.getCell());
            }
            result.add(owningPlayer.getOpponent().getHero().getCell());
        } else if (targetType == SpellTargetType.ALL_FRIENDLY_POWERS) {
            for (Minion friendlyMinion : owningPlayer.getMinionsInPlayGround()) {
                result.add(friendlyMinion.getCell());
            }
            result.add(owningPlayer.getHero().getCell());
        } else if (targetType == SpellTargetType.ALL_ENEMY_IN_COLUMN) {
            result = playGround.enemyInColumn(inputCell, owningPlayer);
        } else if (targetType == SpellTargetType.ALL_ENEMY_IN_ROW) {
            result = owningPlayer.getBattle().getPlayGround().enemyInRow(inputCell, owningPlayer);
        } else if (targetType == SpellTargetType.A_CELL) {
            result.add(playGround.getRandomCell());
        }
        return result;
    }


    public String toString() {
        return "Type : Spell - Name : " + getName() + " - MP : " + MP + " - Desc:" + desc;
    }

    public SpellTargetType getTargetType() {
        return targetType;
    }

    public void setOwningPlayer(Player owningPlayer) {
        this.owningPlayer = owningPlayer;
    }
}
