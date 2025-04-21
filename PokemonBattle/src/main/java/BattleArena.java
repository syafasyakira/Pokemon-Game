import java.util.*;

public class BattleArena {
    private Monster playerMonster;
    private Monster wildMonster;
    private Scanner scanner;

    public BattleArena(Monster playerMonster, Monster wildMonster, Scanner scanner) {
        this.playerMonster = playerMonster;
        this.wildMonster = wildMonster;
        this.scanner = scanner;
    }

    public boolean startBattle() {
        System.out.println("\nBattle starts between " + playerMonster.getName() + " (Level: " + playerMonster.getLevel() + ") and " + wildMonster.getName() + " (Level: " + wildMonster.getLevel() + ")");
        while (playerMonster.getHp() > 0 && wildMonster.getHp() > 0) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Special Attack");
            System.out.println("3. Elemental Attack");
            System.out.println("4. Use Item");
            System.out.println("5. Flee");
            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1:
                        playerMonster.basicAttack(wildMonster);
                        break;
                    case 2:
                        playerMonster.specialAttack(wildMonster);
                        break;
                    case 3:
                        playerMonster.elementalAttack(wildMonster);
                        break;
                    case 4:
                        System.out.println("\nChoose an item:");
                        System.out.println("1. Health Potion");
                        System.out.println("2. Elemental Potion");
                        int itemChoice = scanner.nextInt();
                        if (itemChoice == 1) {
                            playerMonster.useItem("Health Potion");
                        } else if (itemChoice == 2) {
                            playerMonster.useItem("Elemental Potion");
                        }
                        break;
                    case 5:
                        if (playerMonster.flee()) {
                            return askToContinueBattling();
                        } else {
                            System.out.println("Failed to flee. The battle continues.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }
                if (wildMonster.getHp() > 0) {
                    wildMonster.basicAttack(playerMonster);
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    
        if (playerMonster.getHp() > 0) {
            System.out.println(playerMonster.getName() + " won the battle!");
            playerMonster.gainEp(50); // Example EP gain
        } else {
            System.out.println(playerMonster.getName() + " lost the battle...");
        }
        return askToContinueBattling();
    }
    
    private boolean askToContinueBattling() {
        System.out.println("\nDo you want to continue battling in the dungeon? (yes/no)");
        return scanner.next().equalsIgnoreCase("yes");
    }    
}


// Dungeon Class
class Dungeon {
    public Monster generateWildMonster() {
        String[] names = {"Charizard", "Pidgey", "Blastoise", "Articuno", "Geodude"};
        String[] elements = {"Fire", "Wind", "Water", "Ice", "Earth"};
        String name = names[new Random().nextInt(names.length)];
        String element = elements[new Random().nextInt(elements.length)];
        int level = new Random().nextInt(100); // Random level between 0 and 99
        int hp = 100;
        int ep = 0;

        switch (element) {
            case "Fire":
                return new FireMonster(name, level, hp, ep);
            case "Wind":
                return new WindMonster(name, level, hp, ep);
            case "Water":
                return new WaterMonster(name, level, hp, ep);
            case "Ice":
                return new IceMonster(name, level, hp, ep);
            case "Earth":
                return new EarthMonster(name, level, hp, ep);
            default:
                return null;
        }
    }
}