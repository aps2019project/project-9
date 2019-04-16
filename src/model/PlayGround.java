package model;

import java.util.ArrayList;

public class PlayGround {
    private Cell[][] cells = new Cell[5][9];
    public boolean isForEnemyMinion(Cell cell , Player player){return false;}
    public boolean isForFriendlyMinion(Cell cell , Player player){return false;}
    public Cell getCell(int x , int y){
        return cells[x-1][y-1];
    }
    public ArrayList<Cell> getAroundCells(Cell centerCell){
        ArrayList<Cell> result = new ArrayList<>();
        //
        return null;
    }
}
