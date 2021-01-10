package collect.the.coins;

import java.awt.Color;

public class HiddenGold extends Gold {

    public boolean isVisible;

    public HiddenGold(int xPosition, int yPosition, int goldAmount) {
        super(xPosition, yPosition, goldAmount);
        isVisible = false;
        color = new Color(255, 128, 0);
    }
}
