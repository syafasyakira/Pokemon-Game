public class FireMonster extends Monster {
    public FireMonster(String name, int level, int hp, int ep) {
        super(name, Math.max(level, 1), hp, ep, "Fire");
    }
}