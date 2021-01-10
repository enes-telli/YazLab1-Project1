package collect.the.coins;

import java.awt.Color;

public class Gold {

    public int xPos;
    public int yPos;
    public int goldAmount;
    public Color color;

    public Gold(int xPosition, int yPosition, int goldAmount) {
        xPos = xPosition;
        yPos = yPosition;
        this.goldAmount = goldAmount;
        this.color = Color.yellow;
    }
}
