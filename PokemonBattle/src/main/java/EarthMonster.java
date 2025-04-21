public class EarthMonster extends Monster {
    public EarthMonster(String name, int level, int hp, int ep) {
        super(name, Math.max(level, 1), hp, ep, "Earth");
    }
}