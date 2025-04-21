import java.util.*;

public class PokemonBattle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Player> players = Player.loadProgress();
        System.out.println("Welcome to POKEMON BATTLE!");

        System.out.print("\nEnter your player name: ");
        String playerName = scanner.nextLine();
        Player player = players.getOrDefault(playerName, new Player(playerName));
        players.put(playerName, player);

        Homebase homebase = new Homebase(player, scanner);
        homebase.displayMenu(players);

        SaveProgress.save(player, playerName, "progress.txt");

        scanner.close();
        System.out.println("Thank you for playing!");
    }
}