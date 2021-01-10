package collect.the.coins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Results {

    public static void writeAllPlayersResults(Player[] players) throws IOException {
        File file = new File("results.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write("      ADIM SAYISI      HARCANAN ALTIN      KASADAKİ ALTIN      TOPLANAN ALTIN\n");
        for (int i = 0; i < 4; i++) {
            String string = String.format("%-9c%-19d%-20d%-21d%d\n", (char) (i + 65),
                    players[i].totalMoveCount,
                    players[i].totalSpentGold,
                    players[i].goldAmount,
                    players[i].totalEarnedGold);
            output.write(string);
        }
        output.close();
    }

    public static void writePlayerPath(String player, String string) throws IOException {
        File file = new File(player + ".txt");
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file, true));
        output.write(string);
        output.close();
    }

}
