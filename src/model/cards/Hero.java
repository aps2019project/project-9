package model.cards;

import com.google.gson.annotations.Expose;
import model.Cell;
import model.buffs.Buff;
import model.cellaffects.CellAffect;
import model.enumerations.CardType;
import model.enumerations.HeroName;
import model.enumerations.MinionAttackType;

import java.util.ArrayList;

public class Hero extends Minion {
    // hero does not have minion name ( not use the minion name that inherit )
    // for hero MP is the buffs cost
    private static ArrayList<Hero> heroes = new ArrayList<>();
    @Expose
    private int coolDown;
    private int turnsRemained; // for cool down
    @Expose
    private ArrayList<Buff> buffs;
    @Expose
    private CellAffect cellAffect;
    @Expose
    private HeroTargetType buffsTargetType; // for spell
    @Expose
    private HeroName heroName;
    private boolean isSpecialPowerActivated = false;

    public Hero(HeroName heroName, int cost, int HP, int AP, MinionAttackType attackType, int attackRange,
                ArrayList<Buff> buffs, int MP, int coolDown, int cardID, String name, String desc, boolean isFars,
                HeroTargetType buffsTargetType , CellAffect cellAffect) {
        super(name, cost, MP, HP, AP, attackType, attackRange, null, CardType.MINION,cardID,desc,
                null, isFars);
        this.coolDown = coolDown;
        this.buffs = buffs;
        this.buffsTargetType = buffsTargetType;
        this.heroName = heroName;
        this.cellAffect = cellAffect;
    }

    public boolean isSpellReady() {
        return turnsRemained == 0;
    }

    public void useSpecialPower(Cell cell) { // cast spell
        turnsRemained = coolDown;
        if(buffsTargetType == HeroTargetType.ON_ATTACK)
            isSpecialPowerActivated = true;
        else if (buffsTargetType == HeroTargetType.ITSELF) {
            if(buffs != null) {
                for (Buff buff : buffs) {
                    buff.startBuff(getCell());
                }
            }
        }else if(buffsTargetType == HeroTargetType.ALL_POWERS_IN_ROW){
            if(buffs != null){
                for (Cell cell1 : player.getBattle().getPlayGround().enemyInRow(getCell(), player.getOpponent())) {
                    for (Buff buff : buffs) {
                        buff.startBuff(cell1);
                    }
                }
            }
        } else if(buffsTargetType == HeroTargetType.AN_ENEMY_POWER){
            if(buffs != null) {
                Cell target = player.getBattle().getPlayGround().getRandomPowerCell(player);
                for (Buff buff : buffs) {
                    buff.startBuff(target);
                }
            }else if(heroName == HeroName.AFSANE){
                Cell target = player.getBattle().getPlayGround().getRandomPowerCell(player.getOpponent());
                target.getMinionOnIt().dispelPositiveBuffs();
            }
        } else if(buffsTargetType == HeroTargetType.A_CELL){
            if(cellAffect != null) {
                Cell target = player.getBattle().getPlayGround().getRandomCell();
                cellAffect.putCellAffect(target);
            }
        }
        else if(buffsTargetType == HeroTargetType.ALL_ENEMY_POWERS){
            if(buffs != null) {
                for (Minion minion : player.getOpponent().getMinionsInPlayGround()) {
                    for (Buff buff : buffs) {
                        buff.startBuff(minion.getCell());
                    }
                }
            }
        }
    }

    public HeroTargetType getBuffsTargetType() {
        return buffsTargetType;
    }

    public String toString() {
        String string = "Name : " + getName() + " - AP : " + getAP() + " - HP : " + getHP() + " - Class : "
                + getAttackType() + " - Special power: " + getDesc() + " - cost : " + getCost();
        return string;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setTurnsRemainedForNextTurn(){// every turn ( in nextTurn() )
        if(turnsRemained != 0){
            turnsRemained--;
        }
    }

    public HeroName getHeroName() {
        return heroName;
    }

    public boolean isSpecialPowerActivated() {
        return isSpecialPowerActivated;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public void setCellAffect(CellAffect cellAffect) {
        this.cellAffect = cellAffect;
    }

    public CellAffect getCellAffect() {
        return cellAffect;
    }
}
