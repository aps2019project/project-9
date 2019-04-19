package model.cards;

import model.buffs.Buff;
import model.Cell;
import model.Player;
import model.enumerations.CardType;
import model.enumerations.MinionAttackType;
import model.enumerations.MinionName;
import model.enumerations.SpecialPowerActivationTime;
import model.items.Item;
import model.specialPower.OnDefendSpecialPower;
import model.specialPower.OnDefendType;
import model.specialPower.SpecialPower;

import java.util.ArrayList;

public class Minion extends Card{

    protected ArrayList<Buff> activeBuffs;
    protected boolean canCounterAttack;

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    protected boolean canAttack;

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    protected MinionName minionName;
    protected boolean canMove;
    protected boolean isHolly;
    protected MinionAttackType attackType;
    protected int HP;
    protected int AP;
    protected int attackRange;
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

    public SpecialPower getSpecialPower(){
        return specialPower;
    }
    public MinionName getMinionName(){return minionName;}
    public int getReductionOfOthersAttack() {
        return reductionOfOthersAttack;
    }

    public void setReductionOfOthersAttack(int reductionOfOthersAttack) {
        this.reductionOfOthersAttack = reductionOfOthersAttack;
    }

    protected int reductionOfOthersAttack = 0;

    public void putInMap(Cell cell) {
    }

    public void attack(Cell cell) {
    }

    public void killed() {
    }

    public int getAP(){
        return AP;
    }

    public boolean defend(Card attackingCard , Buff buff){
        // this method should be called while this minion is being attacked or a Spell is being casted on it
        if(specialPower.getSpecialPowerActivationTime() != SpecialPowerActivationTime.ON_DEFEND)
            return true;
        // special Power is ONDEFEND
        OnDefendSpecialPower onDefend = (OnDefendSpecialPower)specialPower;
        if(attackingCard.getCardType() == CardType.SPELL){
            if(((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.BUFF){
                if(((OnDefendSpecialPower) specialPower).getDeactivatedBuff() == buff.getBuffName())
                    return false;
            } else if (((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_NEGATIVE){
                if(!buff.isPositiveBuff())
                    return false;
            }
        }else if(attackingCard.getCardType() == CardType.MINION){
            if(((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_NEGATIVE)
                return false;
            else if(((OnDefendSpecialPower) specialPower).getOnDefendType() == OnDefendType.NOT_ATTACK_FROM_WEAK){
                Minion attackingMinion = (Minion)attackingCard;
                if(attackingMinion.getAP() < this.AP){
                    return false;
                }
            }
        }
        return true;
    }

    public void counterAttack(Cell cell) {

    }

    public boolean isValidCell(Cell cell) {
        return false;
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

    public void castSpecialPower() {
    }

    public boolean canCastSpell(Spell spell) {
        return false;
    }

    public boolean canAttackFrom(Card card) {
        return false;
    }

    public void alternate() {
    }

    public void doOnRespawnPower() {
    }

    public void addActiveBuff(Buff buff) {
        if(!activeBuffs.contains(buff))
            activeBuffs.add(buff);
    }

    public void reduceHP(int number) {
        // if HP bellow the 0 , killed()
    }

    public Player getPlayer() {
        return player;
    }

    public void receiveAttack() {
    }

    public int getHP() {
        return HP;
    }

    public String toString() {
        return null;
    }

    public void addHP(int number) {
    }

    public MinionAttackType getAttackType() {
        return attackType;
    }

    public void reduceAP(int number) {
        AP -= number;
        if(HP <= 0)
            killed();
    }

    public void setOnAttackItem(Item item) {

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
        return null;
    }

    public void deleteActiveBuff(Buff buff) {
        activeBuffs.remove(buff);
    }

    public void assignCanCounterAttack(boolean assign) {
        canCounterAttack = assign;
    }
    public boolean equals(Minion minion){
        return this.cardID.equals(minion.cardID);
    }
    public void dispelPositiveBuffs(){
        // if a buff is continous , it should be deactive just this turn
    }
    public void dispelNegativeBuffs(){
        // if a buff is continous , it should be deactive just this turn
    }
    public void addContinuous(Buff buff){
        continuousBuffs.add(buff);
    }
    public boolean hasActiveHollyBuff(){
        return false;
    }

    public boolean getIsHero() {
        return isHero;
    }
}
