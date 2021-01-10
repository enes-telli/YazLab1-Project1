package collect.the.coins;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerD extends Player {

    public PlayerD(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        targetSettingCost = ConfigFrame.targetCosts[3];
        moveCost = ConfigFrame.moveCosts[3];
        playerColor = Color.white;
    }

    @Override
    public void setTheTarget(ArrayList<Gold> golds, ArrayList<HiddenGold> hiddenGolds) {
        if (target == null) {
            if (goldAmount >= targetSettingCost) {
                Gold mostProfitableGold = null;
                Gold secondGold = null;
                if (golds.size() > 0) {
                    mostProfitableGold = golds.get(0);
                }
                int distance = Integer.MAX_VALUE;
                int highestProfit = Integer.MIN_VALUE;

                for (Gold currentGold : golds) {
                    int currentDistance = Math.abs(currentGold.xPos - xPos) + Math.abs(currentGold.yPos - yPos);
                    int currentProfit = currentGold.goldAmount - (currentDistance * moveCost);

                    if (currentProfit > highestProfit) {
                        if (isCloserThanOtherPlayers(currentGold, distance)) {
                            mostProfitableGold = currentGold;
                            highestProfit = currentProfit;
                        } else {
                            secondGold = currentGold;
                        }
                    }
                }
                if (mostProfitableGold == null && golds.size() > 0) {
                    mostProfitableGold = secondGold;
                }
                target = mostProfitableGold;
                goldAmount -= targetSettingCost;
                totalSpentGold += targetSettingCost;
            } else {
                isPlayerEliminated = true;
            }
        }
    }

    private boolean isCloserThanOtherPlayers(Gold gold, int distance) {
        Player[] players = GamePanel.players;
        for (int i = 0; i < 3; i++) {
            int currentDistance = Math.abs(gold.xPos - players[i].xPos) + Math.abs(gold.yPos - players[i].yPos);
            if (players[i].target == gold && distance >= currentDistance) {
                return false;
            }
        }
        return true;
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

                    for (int i = 0; i < 3; i++) {
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
                    Results.writePlayerPath("D", currentPath);
                } catch (IOException ex) {
                    Logger.getLogger(PlayerA.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                isPlayerEliminated = true;
            }
        }
    }

}
