package model.cards;

import model.Cell;

import java.util.ArrayList;

public class  Hero extends Minion{
    private static ArrayList<Hero> heroes;
    private int cooldown;
    private Spell heroSpell;

    public boolean isSpellReady(){return false;}
    public void castSpell(Cell cell){}
    public void useSpecialPower(Cell cell){}
    public String toString(){return null;}
}
