package model.items;

import model.Cell;
import model.enumerations.ItemName;


public class RandomAffect extends Collectible {
    @Override
    public void useItem(Cell cell) {
        if(itemType == ItemName.NOOSH_DAROO){
            owningPlayer.getRandomPower(false).addHP(6);
        }else if(itemType == ItemName.TIR_DOSHAKH){
            owningPlayer.getRandomPower(true).addAP(2);
        }else if(itemType == ItemName.RANDOM_DAMAGE){
            owningPlayer.getRandomPower(false).addAP(2);
        }else if(itemType == ItemName.BLADES_AGILITY){
            owningPlayer.getRandomPower(false).addAP(6);
        }
    }
}
