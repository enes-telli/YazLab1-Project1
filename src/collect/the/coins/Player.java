package collect.the.coins;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

    public int xPos;
    public int yPos;
    public Gold target;
    public int moveCount;
    public int goldAmount;
    public Gold hiddenGold;
    public boolean isPlayerEliminated;

    public int targetSettingCost;
    public int moveCost;
    public Color playerColor;

    public int totalMoveCount;
    public int totalSpentGold;
    public int totalEarnedGold;
    public String currentPath;

    public Player(int xPosition, int yPosition) {
        xPos = xPosition;
        yPos = yPosition;
        target = null;
        moveCount = ConfigFrame.playerMoveCount;
        goldAmount = ConfigFrame.playerGoldAmount;
        hiddenGold = null;
        isPlayerEliminated = false;
        totalMoveCount = 0;
        totalSpentGold = 0;
        totalEarnedGold = 0;
        currentPath = "";
    }

    public abstract void setTheTarget(ArrayList<Gold> golds, ArrayList<HiddenGold> hiddenGolds);

    public abstract void move(ArrayList<Gold> golds, ArrayList<HiddenGold> hiddenGolds, Player[] players);
}
