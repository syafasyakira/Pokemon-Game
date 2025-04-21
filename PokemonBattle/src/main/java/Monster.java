import java.io.*;
import java.util.*;

public abstract class Monster implements MonsterActions, Serializable {
    protected String name;
    protected int level;
    protected int hp;
    protected int ep;
    protected String element;
    protected boolean evolvedInCurrentLevel; // New property to track if the monster has evolved in this level
    protected boolean elementalPotionUsed; // New property to track if an elemental potion was used
    protected String boostedElement; // New property to track the boosted element
    protected int epForNextLevel; // New property to track EP required for the next level

    public Monster(String name, int level, int hp, int ep, String element) {
        this.name = name;
        this.level = level > 0 ? level : 1; 
        this.hp = hp;
        this.ep = ep;
        this.element = element;
        this.evolvedInCurrentLevel = false; // Set initial value to false
        this.elementalPotionUsed = false; // Set initial value to false
        this.boostedElement = null; // Set initial value to null
        this.epForNextLevel = calculateEpForNextLevel(); // Initialize epForNextLevel
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getEp() {
        return ep;
    }

    public String getElement() {
        return element;
    }

    public void gainEp(int amount) {
        this.ep += amount;
        System.out.println(this.name + " gained " + amount + " EP.");
        checkLevelUp();
    }

    private void checkLevelUp() {
        while (this.ep >= this.epForNextLevel) {
            levelUp();
        }
    }

    public void levelUp() {
        this.level++;
        this.hp += 10; // Example increment
        this.epForNextLevel = calculateEpForNextLevel(); // Update EP required for the next level
        this.evolvedInCurrentLevel = false; // Reset evolution status when leveling up
        System.out.println(this.name + " has leveled up! Level is now " + this.level + ". Next level requires " + this.epForNextLevel + " EP.");
    }

    private int calculateEpForNextLevel() {
        return this.level * 100; // Example formula: Level 1 needs 100 EP, Level 2 needs 200 EP, and so on.
    }

    public void evolve(String newElement) throws CustomException {
        if (!evolvedInCurrentLevel) { // Check if the monster has already evolved in this level
            if (canEvolveTo(newElement)) {
                this.element = newElement;
                System.out.println(this.name + " has evolved to " + newElement + " element.");
                this.evolvedInCurrentLevel = true; // Set evolution status to true after evolving
            } else {
                throw new CustomException("Invalid evolution path for " + this.element + " to " + newElement);
            }
        } else {
            throw new CustomException("Monster has already evolved in this level.");
        }
    }

    private boolean canEvolveTo(String newElement) {
        Map<String, List<String>> evolutionPaths = new HashMap<>();
        evolutionPaths.put("Fire", Arrays.asList("Wind", "Earth"));
        evolutionPaths.put("Wind", Arrays.asList("Fire", "Water"));
        evolutionPaths.put("Water", Arrays.asList("Wind", "Ice"));
        evolutionPaths.put("Ice", Arrays.asList("Water", "Earth"));
        evolutionPaths.put("Earth", Arrays.asList("Fire", "Ice"));

        return evolutionPaths.getOrDefault(this.element, Collections.emptyList()).contains(newElement);
    }

    @Override
    public void basicAttack(Monster opponent) {
        System.out.println();
        System.out.println(this.name + " uses Basic Attack on " + opponent.getName());
        opponent.hp -= 10; // Example damage
        System.out.println(opponent.getName() + " HP is now " + opponent.getHp());
    }

    @Override
    public void specialAttack(Monster opponent) throws CustomException {
        System.out.println();
        System.out.println(this.name + " uses Special Attack on " + opponent.getName());
        System.out.println();
        if (new Random().nextInt(10) < 1) {
            throw new CustomException("Special Attack Missed!");
        }
        opponent.hp -= 20; // Example damage
        this.hp -= 5; // Example recoil damage
        System.out.println(opponent.getName() + " HP is now " + opponent.getHp());
        System.out.println(this.name + " HP is now " + this.hp);
    }

    @Override
    public void elementalAttack(Monster opponent) {
        System.out.println();
        System.out.println(this.name + " uses Elemental Attack on " + opponent.getName());
        int damage = calculateElementalDamage(opponent);
        opponent.hp -= damage;
        System.out.println(opponent.getName() + " HP is now " + opponent.getHp());
        if (this.elementalPotionUsed) {
            this.elementalPotionUsed = false;
            this.boostedElement = null;
        }
    }

    private int calculateElementalDamage(Monster opponent) {
        Map<String, String> effectiveness = new HashMap<>();
        effectiveness.put("Fire", "Ice");
        effectiveness.put("Ice", "Wind");
        effectiveness.put("Wind", "Earth");
        effectiveness.put("Earth", "Water");
        effectiveness.put("Water", "Fire");
        
        int baseDamage = 15;
        int boostedDamage = 40; // Higher damage for boosted element

        if (this.elementalPotionUsed && this.boostedElement != null) {
            // If the boosted element is used, inflict higher damage
            if (this.boostedElement.equals(this.element)) {
                return boostedDamage;
            }
        }
        
        if (effectiveness.get(this.element).equals(opponent.getElement())) {
            return 30; // Example effective damage
        }
        return baseDamage; // Example normal damage
    }

    @Override
    public void useItem(String item) {
        if (item.equals("Health Potion")) {
            this.hp += 20; // Example health potion effect
            System.out.println();
            System.out.println(this.name + " uses Health Potion. HP is now " + this.hp);
        } else if (item.equals("Elemental Potion")) {
            this.elementalPotionUsed = true;
            this.boostedElement = this.element;
            System.out.println();
            System.out.println(this.name + " uses Elemental Potion. Elemental attack boosted to " + this.boostedElement);
        }
    }

    @Override
    public boolean flee() {
        System.out.println();
        System.out.println(this.name + " attempts to flee.");
        boolean fled = new Random().nextBoolean();
        if (fled) {
            System.out.println(this.name + " successfully fled.");
        } else {
            System.out.println(this.name + " failed to flee.");
        }
        return fled;
    }
}