public class WaterMonster extends Monster {
    public WaterMonster(String name, int level, int hp, int ep) {
        super(name, Math.max(level, 1), hp, ep, "Water");
    }
}