package model.cards;

import model.PlayGround;
import model.buffs.Buff;
import model.Cell;
import model.Player;
import model.cellaffects.CellAffect;
import model.enumerations.CardType;
import model.enumerations.MinionAttackType;
import model.enumerations.MinionName;
import model.enumerations.SpecialPowerActivationTime;
import model.items.Item;
import model.specialPower.OnDefendSpecialPower;
import model.specialPower.OnDefendType;
import model.specialPower.SpecialPower;

import java.util.ArrayList;

public class Minion extends Card {

    protected ArrayList<Buff> activeBuffs;
    protected boolean canCounterAttack;
    protected boolean canAttack;
    private MinionName minionName;
    protected boolean canMove;
    protected boolean isHolly;
    protected MinionAttackType attackType;
    protected int HP;
    protected int AP;
    protected int attackRange; // for RANGED Type
    protected ArrayList<Buff> continuousBuffs;
    protected SpecialPower specialPower;
    protected boolean isFars;
    private static ArrayList<Minion> minions;
    protected Player player;
    protected ArrayList<Item> activeItems;
    protected Item onAttackItem;
    protected Cell cell;
    protected boolean canComboAttack;
    protected boolean isHero = false;

    public Minion(int cost, int MP, CardType cardType,String name, int cardID, String desc,
                  MinionName minionName, MinionAttackType attackType,
                  int HP, int AP, int attackRange, SpecialPower specialPower, boolean isFars) {
        super(cost, MP, cardType, cardID,name, desc);
        this.minionName = minionName;
        this.attackType = attackType;
        this.HP = HP;
        this.AP = AP;
        this.attackRange = attackRange;
        this.specialPower = specialPower;
        this.isFars = isFars;
        this.isHero = false;
    }


    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public SpecialPower getSpecialPower() {
        return specialPower;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public MinionName getMinionName() {
        return minionName;
    }

    public int getReductionOfOthersAttack() {
        return reductionOfOthersAttack;
    }

    public void setReductionOfOthersAttack(int reductionOfOthersAttack) {
        this.reductionOfOthersAttack = reductionOfOthersAttack;
    }

    protected int reductionOfOthersAttack = 0;

    public void putInMap(Cell cell) {
        // check validation of cell
        if (isValidCell(cell)) {
            player.getHand().deleteCard(this);
            cell.addCard(this);
            if (cell.hasCellAffect()) {
                for (CellAffect cellAffect : cell.getCellAffects()) {
                    cellAffect.castCellAffect(this);
                }
            }
        }
    }

    public void attack(Cell cell) {
    }

    public void killed() {
        // if it has flag in game mode two , put the flag in playGround
        // ( the Cell field is the minion last cell ( before kill ) )
        player.getBattle().checkWinner();
    }

    public int getAP() {
        return AP;
    }

    public boolean defend(Card attackingCard, Buff buff) {
        // this method should be called while this minion is being attacked or a Spell is being casted on it
        if (specialPower.getSpecialPowerActivationTime() != SpecialPowerActivationTime.ON_DEFEND)
            return true;
        // special Power is ONDEFEND
        OnDefendSpecialPower onDefend = (OnDefendSpecialPower) specialPower;
        if (attackingCard.getCardType() == CardType.SPELL) {
            if (((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.BUFF) {
                if (((OnDefendSpecialPower) specialPower).getDeactivatedBuff() == buff.getBuffName())
                    return false;
            } else if (((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_NEGATIVE) {
                if (!buff.isPositiveBuff())
                    return false;
            }
        } else if (attackingCard.getCardType() == CardType.MINION) {
            if (((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_NEGATIVE)
                return false;
            else if (((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_ATTACK_FROM_WEAK) {
                Minion attackingMinion = (Minion) attackingCard;
                if (attackingMinion.getAP() < this.AP) {
                    return false;
                }
            }
        }
        return true;
    }

    public void counterAttack(Cell cell) {
        // check isValidCell() again

    }

    public boolean isValidCell(Cell cell) {
        // for the target cell that this wants to attack ( or Counter Attack ) to
        return player.getBattle().getPlayGround().isValid(getCell(), cell, attackType);
    }

    public void comboAttack(Cell cell, ArrayList<Card> participatingCards) {
    }

    public void castPassiveSpecialPower() {
    }

    public ArrayList<Buff> getActiveBuffs() {
        return activeBuffs;
    }


    public Cell getCell() {
        return cell;
    }


    public void addActiveBuff(Buff buff) {
        if (!activeBuffs.contains(buff))
            activeBuffs.add(buff);
    }

    public void reduceHP(int number) {
        // if HP bellow the 0 , killed()
        if ((HP - number) <= 0) {
            HP = 0;
            killed();
        } else
            HP -= number;
    }

    public Player getPlayer() {
        return player;
    }

    public void receiveAttack() {

    }

    public int getHP() {
        return HP;
    }


    public void addHP(int number) {
        HP += number;
    }

    public MinionAttackType getAttackType() {
        return attackType;
    }

    public void reduceAP(int number) {
        AP -= number;
        if (HP <= 0)
            killed();
    }

    public void setOnAttackItem(Item item) {
        onAttackItem = item;
    }

    public void addAP(int number) {
        AP += number;
    }

    public void addActiveItem(Item item) {
        activeItems.add(item);
    }

    public void deleteActiveItem(Item item) {
        activeItems.remove(item);
    }

    public Minion getNearRandomOpponentPower() {
        //
        int distance = 14; // max ( 5 + 9 )
        Minion targetMinion = null;
        PlayGround playGround = player.getBattle().getPlayGround();
        for (Minion enemyMinion : player.getOpponent().getMinionsInPlayGround()) {
            if (playGround.getManhatanDistance(enemyMinion.getCell()
                    , this.getCell()) < distance) {
                distance = playGround.getManhatanDistance(enemyMinion.getCell()
                        , this.getCell());
                targetMinion = enemyMinion;
            }
        }
        return targetMinion;
    }

    public void deleteActiveBuff(Buff buff) {
        activeBuffs.remove(buff);
    }

    public void assignCanCounterAttack(boolean assign) {
        canCounterAttack = assign;
    }

    public boolean equals(Minion minion) {
        return (this.cardID == (minion.cardID));
    }

    public void dispelPositiveBuffs() {
        // if a buff is continous , it should be deactive just this turn
        for (Buff activeBuff : activeBuffs) {
            if (activeBuff.isPositiveBuff())
                activeBuff.endBuff(this);
        }
    }

    public void dispelNegativeBuffs() {
        // if a buff is continous , it should be deactive just this turn
        for (Buff buff : activeBuffs) {
            if (!buff.isPositiveBuff())
                buff.endBuff(this);
        }
        // continouous should start on nextTurn() in Battle
    }

    public void addContinuous(Buff buff) {
        continuousBuffs.add(buff);
    }

    public boolean getIsHero() {
        return isHero;
    }

    public String toString() {
        return "Type : Minion - Name : " + getName() + " - Class: " + attackType + " - AP : " + AP +
                " - HP : " + HP + " - MP : " + MP + " - Special power : " + desc;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Minion getCopy() {
        return null;
    }
}
