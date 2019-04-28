package model.items;


import model.Cell;
import model.Player;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;

public abstract class Collectible extends Item {
    protected Cell cell;
    protected Spell spell;
    protected ItemTarget target;
    protected Player owningPlayer; // player that has it

    public abstract void useItem();

    public boolean isValidCell(Cell cell) {
        // switch on target and ItemTarget enum
        return false;
    }

    public void collect(Player player) {
        owningPlayer = player;
    }

    Collectible(String name, ItemName itemName, String desc){
        super(0,name,itemName,0,desc);
    }
}
