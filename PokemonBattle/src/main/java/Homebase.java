import java.util.*;

public class Homebase {
    private Player player;
    private Scanner scanner;
    public List<Monster> availableMonsters;

    public Homebase(Player player, Scanner scanner) {
        this.player = player;
        this.scanner = scanner;
        this.availableMonsters = new ArrayList<>(Arrays.asList(
            new FireMonster("Charmander", 1, 100, 0),
            new WindMonster("Aerodactyl", 1, 100, 0),
            new WaterMonster("Squirtle", 1, 100, 0),
            new IceMonster("Glaceon", 1, 100, 0),
            new EarthMonster("Bulbasour", 1, 100, 0)
        ));
    }

    public void displayMenu(Map<String, Player> players) {
        boolean inHomebase = true;
        while (inHomebase) {
            displayMonsterStats();
            System.out.println("\nWelcome to Homebase! Choose an action:");
            System.out.println("1. Heal All Monsters");
            System.out.println("2. Evolve Monster");
            System.out.println("3. Explore Dungeon");
            System.out.println("4. Save Progress");
            System.out.println("5. View All Monster");
            System.out.println("6. Add a new Monster to your collection");
            System.out.println("0. Quit Game");
            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1:
                        player.healAllMonsters();
                        break;
                    case 2:
                        evolveMonsterMenu();
                        break;
                    case 3:
                        selectMonstersForDungeon();
                        break;
                    case 4:
                        Player.saveProgress(players);
                        break;
                    case 5:
                        viewAllMonsters();
                        break;
                    case 6:
                        addNewMonsterToCollection(scanner);
                        break;
                    case 0:
                        inHomebase = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void viewAllMonsters() {
        List<Monster> monsters = player.getMonsters();
        if (monsters.isEmpty()) {
            System.out.println("No monsters in your collection.");
        } else {
            System.out.println("Monsters in your collection:");
            for (Monster monster : monsters) {
                System.out.println(monster.getName() + " (Level: " + monster.getLevel() + ", HP: " + monster.getHp() + ", EP: " + monster.getEp() + ")");
            }
        }
    }
    
    public void addNewMonsterToCollection(Scanner scanner) {
        System.out.println("\nAvailable monsters to add:");
        for (int i = 0; i < availableMonsters.size(); i++) {
            Monster monster = availableMonsters.get(i);
            System.out.println((i + 1) + ". " + monster.getName() + " (Element: " + monster.getElement() + ")");
        }
        System.out.println("Choose a monster to add to your collection (1-" + availableMonsters.size() + "):");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > availableMonsters.size()) {
            System.out.println("Invalid choice. Please try again.");
        } else {
            Monster selectedMonster = availableMonsters.get(choice - 1);
            try {
                player.addMonster(selectedMonster);
                availableMonsters.remove(selectedMonster); // Remove the selected monster from available monsters
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMonsterStats() {
        List<Monster> monsters = player.getMonsters();
        if (!monsters.isEmpty()) {
            System.out.println("\nYour monsters' stats:");
            for (int i = 0; i < monsters.size(); i++) {
                Monster monster = monsters.get(i);
                System.out.println("Name     : " + monster.getName());
                System.out.println("Level    : " + monster.getLevel());
                System.out.println("HP       : " + monster.getHp());
                System.out.println("EP       : " + monster.getEp());
                System.out.println("Element  : " + monster.getElement());
                System.out.println("EP for Next Level: " + monster.epForNextLevel); 
                System.out.println();           }
        } else {
            System.out.println("You don't have any monsters.");
        }
    }

    private void evolveMonsterMenu() throws CustomException {
        List<Monster> monsters = player.getMonsters();
        if (monsters.isEmpty()) {
            System.out.println("You don't have any monsters to evolve.");
            return;
        }

        System.out.println("\nSelect a monster to evolve:");
        for (int i = 0; i < monsters.size(); i++) {
            System.out.println(i + 1 + ". " + monsters.get(i).getName());
        }
        int monsterIndex = scanner.nextInt() - 1;

        System.out.println("\nChoose a new element: Fire, Wind, Water, Ice, Earth");
        String newElement = scanner.next();
        player.evolveMonster(monsterIndex, newElement);
    }

    private void selectMonstersForDungeon() {
        List<Monster> monsters = player.getMonsters();
        if (monsters.isEmpty()) {
            System.out.println("\nYou don't have any monsters to bring to the dungeon.");
            return;
        }
    
        System.out.println("\nSelect up to 3 monsters to bring to the dungeon:");
        List<Monster> selectedMonsters = new ArrayList<>();
        boolean selecting = true;
    
        while (selecting && selectedMonsters.size() < 3) {
            for (int i = 0; i < monsters.size(); i++) {
                System.out.println((i + 1) + ". " + monsters.get(i).getName());
            }
    
            System.out.println("\nEnter the number of the monster to select (or 0 to stop): ");
            int monsterIndex = scanner.nextInt() - 1;
            if (monsterIndex == -1) {
                selecting = false;
            } else if (monsterIndex >= 0 && monsterIndex < monsters.size()) {
                Monster selectedMonster = monsters.get(monsterIndex);
                if (!selectedMonsters.contains(selectedMonster)) {
                    selectedMonsters.add(selectedMonster);
                } else {
                    System.out.println("This monster is already selected. Choose a different monster.");
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    
        if (!selectedMonsters.isEmpty()) {
            Dungeon dungeon = new Dungeon();
            boolean keepBattling = true;
    
            while (keepBattling) {
                Monster wildMonster = dungeon.generateWildMonster();
                Monster battleMonster = chooseBattleMonster(selectedMonsters);
                if (battleMonster != null) {
                    BattleArena arena = new BattleArena(battleMonster, wildMonster, scanner);
                    keepBattling = arena.startBattle();
                } else {
                    keepBattling = false;
                }
            }
        } else {
            System.out.println("No monsters selected for the dungeon.");
        }
    }    

    private Monster chooseBattleMonster(List<Monster> selectedMonsters) {
        System.out.println("\nChoose a monster to use for the battle:");
        for (int i = 0; i < selectedMonsters.size(); i++) {
            Monster monster = selectedMonsters.get(i);
            System.out.println((i + 1) + ". " + monster.getName() + " (Level: " + monster.getLevel() + ", HP: " + monster.getHp() + ", EP: " + monster.getEp() + ")");
        }

        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < selectedMonsters.size()) {
            return selectedMonsters.get(choice);
        } else {
            System.out.println("Invalid choice. No battle will be started.");
            return null;
        }
    }
}