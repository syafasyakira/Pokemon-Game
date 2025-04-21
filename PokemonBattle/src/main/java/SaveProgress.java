import java.io.*;
import java.util.*;

public class SaveProgress {
    public static void save(Player player, String playerName, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(playerName + "\n"); // Tulis nama pemain
            for (Monster monster : player.getMonsters()) {
                writer.write(monster.getClass().getSimpleName() + "," + monster.getName() + "," + monster.getLevel() + "," + monster.getHp() + "," + monster.getEp() + "," + monster.getElement() + "\n");
            }
            System.out.println("Progress saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }

    public static void saveAllPlayers(Map<String, Player> players, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Player> entry : players.entrySet()) {
                String playerName = entry.getKey();
                Player player = entry.getValue();
                writer.write(playerName + "\n"); 
                for (Monster monster : player.getMonsters()) {
                    writer.write(monster.getClass().getSimpleName() + "," + monster.getName() + "," + monster.getLevel() + "," + monster.getHp() + "," + monster.getEp() + "," + monster.getElement() + "\n");
                }
            }
            System.out.println("All players' progress saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving all players' progress: " + e.getMessage());
        }
    }
}