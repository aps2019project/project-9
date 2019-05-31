package model.items;

import model.Cell;
import model.Player;
import model.buffs.Buff;
import model.cards.Spell;
import model.enumerations.ItemName;
import model.items.itemEnumerations.OnDeathTarget;

import java.util.ArrayList;

public class OnDeathUsableItem extends Usable{
    private OnDeathTarget target;
    private ArrayList<Buff> buffs;

    public OnDeathUsableItem(int cost, String name, ItemName itemType,
                             String desc, OnDeathTarget target, ArrayList<Buff> buffs) {
        super(cost, name, itemType, desc);
        this.target = target;
        this.buffs = buffs;
    }

    @Override
    public void castItem(Player player) {
        // no thing
    }

    public void doOnDeathAction(Player player){
        switch (target){
            case A_FRIENDLY_POWER:
                Cell cell = player.getRandomPower(false).getCell();
                for (Buff buff : buffs) {
                    buff.startBuff(cell);
                }
                break;
        }
    }
}
