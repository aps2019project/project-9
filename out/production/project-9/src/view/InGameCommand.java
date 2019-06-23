package view;

import model.Cell;

public enum InGameCommand {
    MOVE,
    ATTACK,
    INSERT,
    NEXT_TURN,
    EXIT;
    Cell firstCell;
    Cell secondCell;
    InGameCommand(){

    }
}
