public interface MonsterActions {
    void basicAttack(Monster opponent);
    void specialAttack(Monster opponent) throws CustomException;
    void elementalAttack(Monster opponent);
    void useItem(String item);
    boolean flee();
}