package collect.the.coins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;

    public static int screenWidth;
    public static int screenHeight;

    public static int columnCount;
    public static int rowCount;
    public static int unitSize;

    GamePanel gamePanel;
    public static JLabel[] playerLabels = new JLabel[4];
    public static JLabel[] targetLabels = new JLabel[4];

    public GameFrame() {
        SetTheFrame();

        gamePanel = new GamePanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setLocation(SCREEN_WIDTH, 0);
        rightPanel.setPreferredSize(new Dimension(300, screenHeight));
        rightPanel.setBackground(Color.lightGray);
        rightPanel.setLayout(new FlowLayout());

        for (int i = 0; i < 4; i++) {
            playerLabels[i] = new JLabel();
            targetLabels[i] = new JLabel();
            playerLabels[i].setBounds(screenWidth + 20, 80 * (i + 1) - 40, 200, 20);
            targetLabels[i].setBounds(screenWidth + 20, 80 * (i + 1) - 10, 200, 20);
            this.add(playerLabels[i]);
            this.add(targetLabels[i]);
        }

        JButton playButton = new JButton("1 Tur Oyna");
        playButton.setBounds(screenWidth + 20, 350, 100, 30);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayButtonActionPerformed(evt);
            }
        });
        this.add(playButton);

        this.getContentPane().add(gamePanel, BorderLayout.WEST);
        this.getContentPane().add(rightPanel);

        this.setTitle("Collect The Coins");
        this.setPreferredSize(new Dimension(screenWidth + 250, screenHeight + ((screenHeight < 400) ? 300 : 100)));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        for (int i = 0; i < 4; i++) {
            GamePanel.players[i].setTheTarget(GamePanel.golds, GamePanel.hiddenGolds);
            playerLabels[i].setText((char) (i + 65) + " Oyuncusu Altın: " + GamePanel.players[i].goldAmount);
            targetLabels[i].setText("Hedef: " + (GamePanel.players[i].target.yPos + 1) + ".Satır - " + (GamePanel.players[i].target.xPos + 1) + ".Sütun");
            try {
                File file = new File(Character.toString((char) (i + 65)) + ".txt");
                if (file.exists()) {
                    file.delete();
                }
                Results.writePlayerPath(Character.toString((char) (i + 65)), " (" + GamePanel.players[i].xPos + "," + GamePanel.players[i].yPos + ")");
            } catch (IOException ex) {
                Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void SetTheFrame() {
        columnCount = ConfigFrame.columnCount;
        rowCount = ConfigFrame.rowCount;

        unitSize = rowCount > columnCount ? (SCREEN_HEIGHT / rowCount) : (SCREEN_WIDTH / columnCount);
        screenWidth = unitSize * columnCount;
        screenHeight = unitSize * rowCount;
    }

    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 0; i < 4; i++) {
            if (!GamePanel.players[i].isPlayerEliminated && !GamePanel.golds.isEmpty()) {
                gamePanel.play();
                return;
            }
        }
        JOptionPane pane = new JOptionPane();
        pane.showMessageDialog(this, "Oyun Bitti, Sonuçlar Dosyalara Yazdırıldı!");
        this.dispose();
        try {
            Results.writeAllPlayersResults(gamePanel.players);
        } catch (IOException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void UpdateTheLabel(int i) {
        playerLabels[i].setText((char) (i + 65) + " Oyuncusu Altın: " + GamePanel.players[i].goldAmount);
        if (GamePanel.players[i].target != null) {
            targetLabels[i].setText("Hedef: " + (GamePanel.players[i].target.yPos + 1) + ".Satır - " + (GamePanel.players[i].target.xPos + 1) + ".Sütun");
        } else {
            targetLabels[i].setText("Hedef: YOK!");
        }
    }
}
