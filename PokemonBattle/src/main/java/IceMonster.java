public class IceMonster extends Monster {
    public IceMonster(String name, int level, int hp, int ep) {
        super(name, Math.max(level, 1), hp, ep, "Ice");
    }
}