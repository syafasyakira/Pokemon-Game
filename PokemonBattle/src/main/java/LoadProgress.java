import java.io.*;
import java.util.*;

public class LoadProgress {
    public static Map<String, Player> loadProgress(String filename) {
        Map<String, Player> players = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String playerName;
            while ((playerName = reader.readLine()) != null) {
                Player player = new Player(playerName);
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    String[] data = line.split(",");
                    String monsterType = data[0];
                    String name = data[1];
                    int level = Integer.parseInt(data[2]);
                    int hp = Integer.parseInt(data[3]);
                    int ep = Integer.parseInt(data[4]);
                    String element = data[5];
                    Monster monster;
                    switch (monsterType) {
                        case "FireMonster":
                            monster = new FireMonster(name, level, hp, ep);
                            break;
                        case "WindMonster":
                            monster = new WindMonster(name, level, hp, ep);
                            break;
                        case "WaterMonster":
                            monster = new WaterMonster(name, level, hp, ep);
                            break;
                        case "IceMonster":
                            monster = new IceMonster(name, level, hp, ep);
                            break;
                        case "EarthMonster":
                            monster = new EarthMonster(name, level, hp, ep);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown monster type: " + monsterType);
                    }
                    player.addMonster(monster);
                }
                players.put(playerName, player);
            }
            System.out.println("Progress loaded from " + filename);
        } catch (IOException | CustomException | IllegalArgumentException e) {
            System.out.println("Error loading progress: " + e.getMessage());
        }
        return players;
    }
}