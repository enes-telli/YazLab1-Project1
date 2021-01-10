package collect.the.coins;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerB extends Player {

    public PlayerB(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        targetSettingCost = ConfigFrame.targetCosts[1];
        moveCost = ConfigFrame.moveCosts[1];
        playerColor = Color.green;
    }

    @Override
    public void setTheTarget(ArrayList<Gold> golds, ArrayList<HiddenGold> hiddenGolds) {
        if (target == null) {
            if (goldAmount >= targetSettingCost) {
                Gold mostProfitableGold = null;
                int distance = Integer.MAX_VALUE;
                int highestProfit = Integer.MIN_VALUE;

                for (Gold currentGold : golds) {
                    int currentDistance = Math.abs(currentGold.xPos - xPos) + Math.abs(currentGold.yPos - yPos);
                    int currentProfit = currentGold.goldAmount - (currentDistance * moveCost);

                    if (currentProfit > highestProfit) {
                        mostProfitableGold = currentGold;
                        highestProfit = currentProfit;
                    }
                }
                target = mostProfitableGold;
                goldAmount -= targetSettingCost;
                totalSpentGold += targetSettingCost;
            } else {
                isPlayerEliminated = true;
            }
        }
    }

    @Override
    public void move(ArrayList<Gold> golds, ArrayList<HiddenGold> hiddenGolds, Player[] players) {
        if (target != null) {
            int stepsCount = Math.abs(target.xPos - xPos) + Math.abs(target.yPos - yPos);
            stepsCount = (stepsCount > moveCount) ? moveCount : stepsCount;
            currentPath = "";

            if (goldAmount >= (moveCost * stepsCount)) {
                int steps = Math.abs(target.xPos - xPos) + Math.abs(target.yPos - yPos);

                if (steps <= moveCount) {
                    int addToX = (target.xPos - xPos > 0) ? 1 : -1;
                    int xDifference = Math.abs(target.xPos - xPos);

                    for (int i = 0; i < xDifference; i++) {
                        if (hiddenGold != null) {
                            golds.add(hiddenGold);
                            hiddenGolds.remove(hiddenGold);
                            hiddenGold = null;
                        }
                        xPos += addToX;
                        totalMoveCount++;
                        currentPath += (" (" + xPos + "," + yPos + ")");

                        for (HiddenGold currentGold : hiddenGolds) {
                            if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                hiddenGold = currentGold;
                                break;
                            }
                        }
                    }
                    int addToY = (target.yPos - yPos > 0) ? 1 : -1;
                    int yDifference = Math.abs(target.yPos - yPos);

                    for (int i = 0; i < yDifference; i++) {
                        if (hiddenGold != null) {
                            golds.add(hiddenGold);
                            hiddenGolds.remove(hiddenGold);
                            hiddenGold = null;
                        }
                        yPos += addToY;
                        totalMoveCount++;
                        currentPath += (" (" + xPos + "," + yPos + ")");

                        for (HiddenGold currentGold : hiddenGolds) {
                            if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                hiddenGold = currentGold;
                                break;
                            }
                        }
                    }
                    currentPath += "\n";
                    totalSpentGold += (moveCost * steps);
                    totalEarnedGold += target.goldAmount;
                    goldAmount += target.goldAmount - (moveCost * steps);

                    if (golds.contains(target)) {
                        golds.remove(target);
                    } else if (hiddenGolds.contains(target)) {
                        hiddenGolds.remove(target);
                    }
                    for (int i = 0; i < 4; i++) {
                        if (i == 1) {
                            continue;
                        }
                        if (players[i].target == target && players[i].target == target) {
                            players[i].target = null;
                        }
                    }
                    target = null;
                } else {
                    Random random = new Random();
                    int minAxis;

                    if (Math.abs(target.xPos - xPos) < Math.abs(target.yPos - yPos)) {
                        minAxis = Math.abs(target.xPos - xPos);
                        int xMovement = random.nextInt(minAxis + 1);

                        if (xMovement > moveCount) {
                            xMovement = moveCount;
                        }
                        int addToX = (target.xPos - xPos > 0) ? 1 : -1;

                        for (int i = 0; i < xMovement; i++) {
                            if (hiddenGold != null) {
                                golds.add(hiddenGold);
                                hiddenGolds.remove(hiddenGold);
                                hiddenGold = null;
                            }
                            xPos += addToX;
                            totalMoveCount++;
                            currentPath += (" (" + xPos + "," + yPos + ")");

                            for (HiddenGold currentGold : hiddenGolds) {
                                if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                    hiddenGold = currentGold;
                                    break;
                                }
                            }
                        }
                        int yMovement = moveCount - xMovement;
                        int addToY = (target.yPos - yPos > 0) ? 1 : -1;

                        for (int i = 0; i < yMovement; i++) {
                            if (hiddenGold != null) {
                                golds.add(hiddenGold);
                                hiddenGolds.remove(hiddenGold);
                                hiddenGold = null;
                            }
                            yPos += addToY;
                            totalMoveCount++;
                            currentPath += (" (" + xPos + "," + yPos + ")");

                            for (HiddenGold currentGold : hiddenGolds) {
                                if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                    hiddenGold = currentGold;
                                    break;
                                }
                            }
                        }
                        currentPath += "\n";
                    } else {
                        minAxis = Math.abs(target.yPos - yPos);
                        int yMovement = random.nextInt(minAxis + 1);

                        if (yMovement > moveCount) {
                            yMovement = moveCount;
                        }
                        int addToY = (target.yPos - yPos > 0) ? 1 : -1;

                        for (int i = 0; i < yMovement; i++) {
                            if (hiddenGold != null) {
                                golds.add(hiddenGold);
                                hiddenGolds.remove(hiddenGold);
                                hiddenGold = null;
                            }
                            yPos += addToY;
                            totalMoveCount++;
                            currentPath += (" (" + xPos + "," + yPos + ")");

                            for (HiddenGold currentGold : hiddenGolds) {
                                if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                    hiddenGold = currentGold;
                                    break;
                                }
                            }
                        }
                        int xMovement = moveCount - yMovement;
                        int addToX = (target.xPos - xPos > 0) ? 1 : -1;

                        for (int i = 0; i < xMovement; i++) {
                            if (hiddenGold != null) {
                                golds.add(hiddenGold);
                                hiddenGolds.remove(hiddenGold);
                                hiddenGold = null;
                            }
                            xPos += addToX;
                            totalMoveCount++;
                            currentPath += (" (" + xPos + "," + yPos + ")");

                            for (HiddenGold currentGold : hiddenGolds) {
                                if (!currentGold.isVisible && xPos == currentGold.xPos && yPos == currentGold.yPos) {
                                    hiddenGold = currentGold;
                                    break;
                                }
                            }
                        }
                        currentPath += "\n";
                    }
                    totalSpentGold += (moveCost * moveCount);
                    goldAmount -= (moveCost * moveCount);
                }
                try {
                    Results.writePlayerPath("B", currentPath);
                } catch (IOException ex) {
                    Logger.getLogger(PlayerA.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                isPlayerEliminated = true;
            }
        }
    }

}
