package model.items;


import model.Cell;
import model.Player;
import model.enumerations.ItemName;
import model.enumerations.ItemTarget;
import model.enumerations.MinionAttackType;

public abstract class Collectible extends Item {
    protected transient Player owningPlayer; // player that has it

    public Collectible(int cost, String name, ItemName itemType,
                       String desc) {
        super(cost, name, itemType, desc);
    }

    public abstract void useItem();

    public boolean isValidCell(Cell cell) {
        return true;
    }

    public void collect(Player player) {
        owningPlayer = player;
    }//set owning player

    // 8

}
