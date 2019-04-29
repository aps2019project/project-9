package model.cards;

import model.PlayGround;
import model.buffs.Buff;
import model.Cell;
import model.Player;
import model.cellaffects.CellAffect;
import model.enumerations.*;
import model.items.Item;
import model.items.OnAttackSpell;
import model.specialPower.OnAttackSpecialPower;
import model.specialPower.OnDefendSpecialPower;
import model.specialPower.OnDefendType;
import model.specialPower.SpecialPower;

import java.util.ArrayList;

public class Minion extends Card {
    private static ArrayList<Minion> minions;
    protected ArrayList<Buff> activeBuffs;
    protected boolean canCounterAttack;
    protected boolean canAttack;
    private MinionName minionName;
    protected boolean canMove;
    protected MinionAttackType attackType;
    protected int HP;
    protected int AP;
    protected int attackRange; // for RANGED Type
    protected ArrayList<Buff> continuousBuffs = new ArrayList<>();
    protected SpecialPower specialPower;
    protected boolean isFars;
    protected Player player;
    protected ArrayList<Item> activeItems;
    protected Item onAttackItem;
    protected Cell cell;
    protected boolean isHero;
    protected int reductionOfOthersAttack = 0;
    protected boolean hasHollyBuff;

    public Minion(String name, int cost, int MP, int HP, int AP, MinionAttackType attackType,
                  int attackRange, SpecialPower specialPower, CardType cardType, int cardID,
                  String desc, MinionName minionName, boolean isFars) {
        super(cost, MP, cardType, cardID, name, desc);
        this.minionName = minionName;
        this.attackType = attackType;
        this.HP = HP;
        this.AP = AP;
        this.attackRange = attackRange;
        this.specialPower = specialPower;
        this.isFars = isFars;
        this.isHero = false;
        specialPower.setMinion(this);
        hasHollyBuff = false;
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

    public void putInMap(Cell cell) { // the validation of cell is checked in controller
        player.getHand().deleteCard(this);
        player.addMinionInPlayGroundMinions(this);
        cell.addCard(this);
        if (cell.hasCellAffect()) {
            for (CellAffect cellAffect : cell.getCellAffects()) {
                cellAffect.castCellAffect(this);
            }
        }
    }

    public void attack(Cell cell) {
        // this minion attacking to the cell ( the minion on the cell )
        // receiveAttack() of opponent
        // if opponent has flag , get it ( in mode -> one flag )
        if (onAttackItem != null) {
            ((OnAttackSpell)onAttackItem).doOnAttack(cell);
        }
        if (canAttack && isValidCell(cell)) {
            if (this instanceof Hero) {
                if (((Hero) this).getSpellTargetType() == HeroTargetType.ON_ATTACK &&
                        ((Hero) this).isSpecialPowerActivated())
                    ((Hero) this).useSpecialPower(cell);
            }
            if (cell.hasCardOnIt()) {
                Minion opponent = cell.getMinionOnIt();
                if (player.getBattle().getGameMode() == GameMode.ONE_FLAG
                        && player.getBattle().getPlayGround().getFlag().getCurrentCell().equals(opponent.getCell())) {
                    // opponent had flag
                    player.collectFlag(player.getBattle().getPlayGround().getFlag(), this);
                }
                if (opponent.canDefend(this, null)) { // reduce HP
                    if (specialPower.getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_ATTACK
                            && ((OnAttackSpecialPower) specialPower).isAntiHolly()
                            && opponent.hasHollyBuff) {
                        opponent.reduceHP(this.AP);
                    } else
                        opponent.reduceHP(this.AP - opponent.getReductionOfOthersAttack());
                    if (opponent.canCounterAttack && opponent.isValidCell(this.getCell())) {
                        this.reduceHP(opponent.AP - this.getReductionOfOthersAttack());
                    }
                }
            }
            if (specialPower != null
                    && specialPower.getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_ATTACK) {
                if (((OnAttackSpecialPower) specialPower).isDispel()) {
                    if (cell.getMinionOnIt() != null)
                        cell.getMinionOnIt().dispelPositiveBuffs();
                }
                specialPower.castSpecialPower(cell);
            }
            canAttack = false;
            player.getBattle().checkWinner();
        }
    }

    public void killed() {
        // if it has flag in game mode two , put the flag in playGround
        // player.minions in battle ground -> delete
        // ( the Cell field is the minion last cell ( before kill ) )
        if (player.getBattle().getGameMode() == GameMode.ONE_FLAG) {
            if (player.getBattle().getPlayGround().getFlag().getOwningMinion().equals(this)) {
                player.missFlag(player.getBattle().getPlayGround().getFlag());
            }
        } else if (player.getBattle().getGameMode() == GameMode.FLAGS) {
            if (getCell().getFlag() != null) {
                player.missFlag(getCell().getFlag());
            }
        }
        getCell().setMinionOnIt(null);
        player.minionDead(this);
        player.getBattle().checkWinner();
        if (player.getUsableItem().getItemType() == ItemName.SOUL_EATER) {
            player.castUsableItem();
        }
    }

    public int getAP() {
        return AP;
    }

    public boolean canDefend(Card attackingCard, Buff buff) {
        // this method should be called while this minion is being attacked or a Spell is being casted on it
        if (specialPower.getSpecialPowerActivationTime() != SpecialPowerActivationTime.ON_DEFEND)
            return true;
        // special Power is ONDEFEND
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


    public boolean isValidCell(Cell cell) {
        // for the target cell that this wants to attack ( or Counter Attack ) to
        return player.getBattle().getPlayGround().isValid(getCell(), cell, attackType);
    }

    public ArrayList<Buff> getActiveBuffs() {
        return activeBuffs;
    }

    public Cell getCell() {
        return cell;
    }


    public void addActiveBuff(Buff buff) {
        if (!activeBuffs.contains(buff)) {
            if (!(specialPower.getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_DEFEND
                    && ((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.BUFF
                    && ((OnDefendSpecialPower) specialPower).getDeactivatedBuff() == buff.getBuffName())) {
                if (!(specialPower.getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_DEFEND
                        && ((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_NEGATIVE
                        && !buff.isPositiveBuff()))
                    activeBuffs.add(buff);
            }
        }
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
        if (AP < 0)
            AP = 0;
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


    public void assignCanCounterAttack(boolean assign) {
        canCounterAttack = assign;
    }

    public boolean equals(Minion minion) {
        return (this.cardID == (minion.cardID));
    }

    public void dispelPositiveBuffs() {
        // if a buff is continous , it should be deactive just this turn
        ArrayList<Buff> toDelete = new ArrayList<>();
        for (Buff activeBuff : activeBuffs) {
            if (activeBuff.isPositiveBuff()) {
                toDelete.add(activeBuff);
                activeBuff.endBuff(this);
            }
        }
        for (Buff buff : toDelete) {
            activeBuffs.remove(buff);
        }
    }

    public void dispelNegativeBuffs() {
        // if a buff is continous , it should be deactive just this turn
        ArrayList<Buff> toDelete = new ArrayList<>();
        for (Buff buff : activeBuffs) {
            if (!buff.isPositiveBuff()) {
                toDelete.add(buff);
                buff.endBuff(this);
            }
        }
        for (Buff buff : toDelete) {
            activeBuffs.remove(buff);
        }
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

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }


    public boolean isValidCellForMove(Cell targetCell) {
        if (player.getBattle().getPlayGround().getManhatanDistance(this.cell, targetCell) > 2
                || targetCell.hasCardOnIt()
                || !player.getBattle().getPlayGround().canMoveThroughPath(this.cell, targetCell)) {
            return false;
        }
        return true;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ArrayList<Buff> getContinuousBuffs() {
        return continuousBuffs;
    }

    public void buffDeactivated(Buff buff) { // just remove from list of buffs
        activeBuffs.remove(buff);
    }

    public void gotHollyBuff() { // for DARANDE_SHIR
        hasHollyBuff = true;
    }

    public void missedHollyBuff() { // for DARANDE_SHIR
        hasHollyBuff = false;
    }
}
