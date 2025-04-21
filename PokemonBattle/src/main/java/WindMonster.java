public class WindMonster extends Monster {
    public WindMonster(String name, int level, int hp, int ep) {
        super(name, Math.max(level, 1), hp, ep, "Wind");
    }
}