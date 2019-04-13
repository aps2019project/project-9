package model.cards;

import model.Battle;
import model.Buff;
import model.Cell;
import model.enumerations.MinionAttackType;
import model.items.Item;

import java.util.ArrayList;

public class Minion {
    protected ArrayList<Buff> activeBuffs;
    protected boolean canCounterAttack;
    protected boolean canAttack;
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
    protected Battle battle;
    protected ArrayList<Item> activeItems;
    protected Item onAttackItem;
    protected Cell cell;

    public void putInMap(Cell cell) {
    }
    public void attack(Cell cell){}
    public void killed(){}
    public void counterAttack(Cell cell){}
    public boolean isValidCell(Cell cell){return false;}
    public void comboAttack(Cell cell,ArrayList<Card> participatingCards){}
    public void castPassiveSpecialPower(){}
    public Cell getCell(){return cell;}
    public void castSpecialPower(){}
    public boolean canCastSpell(Spell spell){return false;}
    public boolean canAttackFrom(Card card){return false;}
    public void alternate(){}
    public void doOnRespawnPower(){}
    public void addBuff(Buff buff){}
    public void reduceHP(int number){}

    public Battle getBattle() {
        return battle;
    }
    public void receiveAttack(){}
    public int getHP(){
        return HP;
    }
    public String toString(){return null;}
    public void addHP(int number){}

    public MinionAttackType getAttackType() {
        return attackType;
    }
    public void reduceAP(int number){
        AP-=number;
    }
    public void setOnAttackItem(Item item){

    }
}
