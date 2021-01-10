package collect.the.coins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public static int screenWidth;
    public static int screenHeight;
    public static int columnCount;
    public static int rowCount;
    public static int unitSize;

    private int goldRate;
    private int hiddenGoldRate;
    public static int totalGoldCount;
    private int goldCount;
    private int hiddenGoldCount;
    public static ArrayList<Gold> golds = new ArrayList<>();
    public static ArrayList<HiddenGold> hiddenGolds = new ArrayList<>();
    private ArrayList<Integer> possiblePositions = new ArrayList<>();

    Random random = new Random();

    public static Player[] players = new Player[4];

    public GamePanel() {

        columnCount = ConfigFrame.columnCount;
        rowCount = ConfigFrame.rowCount;
        goldRate = ConfigFrame.goldRate;
        hiddenGoldRate = ConfigFrame.hiddenGoldRate;

        totalGoldCount = (int) ((float) columnCount * rowCount * goldRate / 100);
        hiddenGoldCount = (int) ((float) totalGoldCount * hiddenGoldRate / 100);
        goldCount = totalGoldCount - hiddenGoldCount;

        unitSize = GameFrame.unitSize;
        screenWidth = GameFrame.screenWidth;
        screenHeight = GameFrame.screenHeight;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.gray);

        for (int i = 0; i < columnCount * rowCount; i++) {
            if (i == 0 || i == columnCount - 1 || i == columnCount * (rowCount - 1) || i == columnCount * rowCount - 1) {
                continue;
            }
            possiblePositions.add(i);
        }
        initializeGolds();
        initializeHiddenGolds();

        players[0] = new PlayerA(0, 0);
        players[1] = new PlayerB(columnCount - 1, 0);
        players[2] = new PlayerC(0, rowCount - 1);
        players[3] = new PlayerD(columnCount - 1, rowCount - 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGolds(g);
        drawPlayers(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i <= rowCount; i++) {
            g.drawLine(0, i * (screenHeight / rowCount), unitSize * columnCount, i * (screenHeight / rowCount));
        }
        for (int i = 0; i <= columnCount; i++) {
            g.drawLine(i * (screenWidth / columnCount), 0, i * (screenWidth / columnCount), unitSize * rowCount);
        }
    }

    private void drawPlayers(Graphics g) {
        for (int i = 0; i < 4; i++) {
            g.setColor(players[i].playerColor);
            g.fillRect(players[i].xPos * unitSize, players[i].yPos * unitSize, unitSize, unitSize);
            g.setFont(new Font("Default", Font.BOLD, (screenWidth > screenHeight ? screenWidth : screenHeight) / 40));
            g.drawString(Character.toString((char) (i + 65)), players[i].xPos * unitSize + unitSize / 8, players[i].yPos * unitSize);
        }
    }

    private void initializeGolds() {
        for (int i = 0; i < goldCount; i++) {
            int randomIndex = random.nextInt(possiblePositions.size());
            int randomPos = possiblePositions.get(randomIndex);
            int yPos = randomPos / columnCount;
            int xPos = randomPos % columnCount;
            int goldAmount = (random.nextInt(4) + 1) * 5;
            golds.add(new Gold(xPos, yPos, goldAmount));
            possiblePositions.remove(randomIndex);
        }
    }

    private void initializeHiddenGolds() {
        for (int i = 0; i < hiddenGoldCount; i++) {
            int randomIndex = random.nextInt(possiblePositions.size());
            int randomPos = possiblePositions.get(randomIndex);
            int yPos = randomPos / columnCount;
            int xPos = randomPos % columnCount;
            int goldAmount = (random.nextInt(4) + 1) * 5;
            hiddenGolds.add(new HiddenGold(xPos, yPos, goldAmount));
            possiblePositions.remove(randomIndex);
        }
    }

    private void drawGolds(Graphics g) {
        for (int i = 0; i < golds.size(); i++) {
            g.setColor(golds.get(i).color);
            int xPos = golds.get(i).xPos;
            int yPos = golds.get(i).yPos;
            float rate = (float) golds.get(i).goldAmount / 20;  // rates: 0.25 - 0.5 - 0.75 - 1
            int goldSize = (int) (unitSize * rate);
            int padding = (unitSize - goldSize) / 2;
            g.fillOval(xPos * unitSize + padding, yPos * unitSize + padding, goldSize, goldSize);
        }
    }

    public void play() {
        for (int i = 0; i < 4; i++) {
            players[i].setTheTarget(golds, hiddenGolds);
            players[i].move(golds, hiddenGolds, players);
            players[i].setTheTarget(golds, hiddenGolds); // eğer hedefi zaten varsa parası gitmez, labellar için burası!
            GameFrame.UpdateTheLabel(i);
        }
        repaint();
    }
}
