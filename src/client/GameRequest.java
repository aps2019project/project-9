package client;

import model.enumerations.GameMode;

public class GameRequest {
    String userRequested;
    GameMode gameMode;
    int numberOfFlags;

    public GameRequest(String userRequested, GameMode gameMode, int numberOfFlags) {
        this.userRequested = userRequested;
        this.gameMode = gameMode;
        this.numberOfFlags = numberOfFlags;
    }

    public String getUserRequested() {
        return userRequested;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }
}
