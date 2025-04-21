import java.io.*;
import java.util.*;

public class Player implements Serializable {
    private String name;
    private List<Monster> monsters;

    public Player(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
    }

    public void addMonster(Monster monster) throws CustomException {
        this.monsters.add(monster);
        System.out.println("\n" + monster.getName() + " (Level: " + monster.getLevel() + ") has been added to the player's collection.");
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void healAllMonsters() {
        System.out.println();
        for (Monster monster : monsters) {
            monster.hp = 100; // Example full heal
            System.out.println(monster.getName() + " has been fully healed.");
        }
    }

    public void evolveMonster(int monsterIndex, String newElement) throws CustomException {
        if (monsterIndex < 0 || monsterIndex >= monsters.size()) {
            throw new CustomException("\nInvalid monster index.");
        }
        Monster monster = monsters.get(monsterIndex);
        if (monster.getEp() < 10) {
            throw new CustomException("\nNot enough EP to evolve.");
        }
        monster.evolve(newElement);
        System.out.println(monster.getName() + " has evolved to " + newElement + " element.");
    }

    public static void saveProgress(Map<String, Player> players) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("players_data.ser"))) {
            out.writeObject(players);
            System.out.println("Game progress saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving game progress.");
        }
    }

    public static Map<String, Player> loadProgress() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("players_data.ser"))) {
            return (Map<String, Player>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved game found or failed to load.");
            return new HashMap<>();
        }
    }
}