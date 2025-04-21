import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class PokemonBattleGUI {
    private JFrame frame;
    private JTextArea battleLog;
    private Player player;
    private Monster currentMonster;
    private Monster wildMonster;
    private Dungeon dungeon;
    private JButton attackButton;
    private JButton specialAttackButton;
    private JButton elementalAttackButton;
    private JButton useItemButton;
    private JButton fleeButton;
    private JLabel homebase, gambardungeon;
    private JLabel gambarCharmander, gambarAerodactyl, gambarSquirtle, gambarGlaceon, gambarBulbasour;
    private JLabel gambarCharizard, gambarPidgey, gambarBlastoise, gambarArticuno, gambarGeodude;
    private String playerName;
    private List<Monster> availableMonsters;
    private DefaultListModel<String> monsterListModel;
    private JTextArea outputArea;

    public PokemonBattleGUI() {
        availableMonsters = new ArrayList<>();  
        availableMonsters.add(new FireMonster("Charmander", 1, 100, 0));
        availableMonsters.add(new WaterMonster("Squirtle", 1, 100, 0));
        availableMonsters.add(new WindMonster("Aerodactyl", 1, 100, 0));
        availableMonsters.add(new IceMonster("Glaceon", 1, 100, 0));
        availableMonsters.add(new EarthMonster("Bulbasour", 1, 100, 0));
        
        frame = new JFrame("Pokémon Game");
        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        battleLog = new JTextArea();
        battleLog.setBounds(20,10,150,1300);
        battleLog.setSize(1400,50);
        battleLog.setEditable(false);
        battleLog.setOpaque(false);
        battleLog.setFont(new Font("Serif", Font.BOLD, 30));
        battleLog.setForeground(Color.white);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2,6));
        controlPanel.setOpaque(false);

        JButton healButton = new JButton("Heal All Monsters");
        healButton.setBackground(Color.WHITE);
        JButton evolveButton = new JButton("Evolve Monster");
        evolveButton.setBackground(Color.WHITE);
        JButton exploreButton = new JButton("Explore Dungeon + Battle");
        exploreButton.setBackground(Color.WHITE);
        JButton saveButton = new JButton("Save Progress");
        saveButton.setBackground(Color.WHITE);
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.red);
        quitButton.setForeground(Color.white);
        JButton viewButton = new JButton("View All Monster");
        viewButton.setBackground(Color.WHITE);
        JButton addButton = new JButton("Add Monster to Collection");
        addButton.setBackground(Color.WHITE);

        controlPanel.add(healButton);
        controlPanel.add(evolveButton);
        controlPanel.add(exploreButton);
        controlPanel.add(saveButton);
        controlPanel.add(viewButton);
        controlPanel.add(addButton);
                
        attackButton = new JButton("Basic Attack");
        attackButton.setBackground(Color.WHITE);
        specialAttackButton = new JButton("Special Attack");
        specialAttackButton.setBackground(Color.WHITE);
        elementalAttackButton = new JButton("Elemental Attack");
        elementalAttackButton.setBackground(Color.WHITE);
        useItemButton = new JButton("Use Item");
        useItemButton.setBackground(Color.WHITE);
        fleeButton = new JButton("Flee");
        fleeButton.setBackground(Color.WHITE);

        controlPanel.add(attackButton);
        controlPanel.add(specialAttackButton);
        controlPanel.add(elementalAttackButton);
        controlPanel.add(useItemButton);
        controlPanel.add(fleeButton);
        controlPanel.add(quitButton);

        setBattleButtonsEnabled(false);
        
        ImageIcon padangRumput = new ImageIcon("homebase.jpeg");
        Image scaledPadangRumput = padangRumput.getImage().getScaledInstance(1400, 1000, Image.SCALE_SMOOTH);
        homebase = new JLabel(new ImageIcon(scaledPadangRumput));
        homebase.setSize(1300, 800);
        homebase.setVisible(true);
        
        ImageIcon padangRumputMalam = new ImageIcon("dungeon.jpg");
        Image scaledPadangRumputMalam = padangRumputMalam.getImage().getScaledInstance(1400, 770, Image.SCALE_SMOOTH);
        gambardungeon = new JLabel(new ImageIcon(scaledPadangRumputMalam));
        gambardungeon.setSize(1300, 800);
        gambardungeon.setVisible(false);
        
        gambarCharmander = new JLabel();
        ImageIcon charmander = new ImageIcon("charmander.png");
        ImageIcon scaledCharmander = new ImageIcon(charmander.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        gambarCharmander.setIcon(scaledCharmander);
        gambardungeon.add(gambarCharmander);
        gambarCharmander.setVisible(false);
        gambarCharmander.setBounds(210,170,600,600);
        
        gambarAerodactyl = new JLabel();
        ImageIcon aerodactyl = new ImageIcon("aerodactyl.png");
        ImageIcon scaledAerodactyl = new ImageIcon(aerodactyl.getImage().getScaledInstance(600, 450, Image.SCALE_SMOOTH));
        gambarAerodactyl.setIcon(scaledAerodactyl);
        gambardungeon.add(gambarAerodactyl);
        gambarAerodactyl.setVisible(false);
        gambarAerodactyl.setBounds(110,170,600,600);
        
        gambarSquirtle = new JLabel();
        ImageIcon squirtle = new ImageIcon("squirtle.png");
        ImageIcon scaledSquirtle = new ImageIcon(squirtle.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        gambarSquirtle.setIcon(scaledSquirtle);
        gambardungeon.add(gambarSquirtle);
        gambarSquirtle.setVisible(false);
        gambarSquirtle.setBounds(210,210,600,600);
        
        gambarGlaceon = new JLabel();
        ImageIcon glaceon = new ImageIcon("glaceon.png");
        ImageIcon scaledGlaceon = new ImageIcon(glaceon.getImage().getScaledInstance(450, 400, Image.SCALE_SMOOTH));
        gambarGlaceon.setIcon(scaledGlaceon);
        gambardungeon.add(gambarGlaceon);
        gambarGlaceon.setVisible(false);
        gambarGlaceon.setBounds(150,167,600,600);
        
        gambarBulbasour = new JLabel();
        ImageIcon bulbasour = new ImageIcon("bulbasour.png");
        ImageIcon scaledBulbasour = new ImageIcon(bulbasour.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        gambarBulbasour.setIcon(scaledBulbasour);
        gambardungeon.add(gambarBulbasour);
        gambarBulbasour.setVisible(true);
        gambarBulbasour.setBounds(210,220,600,600);
        
        gambarCharizard = new JLabel();
        ImageIcon charizard = new ImageIcon("charizard.png");
        ImageIcon scaledCharizard = new ImageIcon(charizard.getImage().getScaledInstance(1000, 450, Image.SCALE_SMOOTH));
        gambarCharizard.setIcon(scaledCharizard);
        gambardungeon.add(gambarCharizard);
        gambarCharizard.setVisible(false);
        gambarCharizard.setBounds(470,185,1000,600);
        
        gambarPidgey = new JLabel();
        ImageIcon pidgey = new ImageIcon("pidgey.png");
        ImageIcon scaledPidgey = new ImageIcon(pidgey.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
        gambarPidgey.setIcon(scaledPidgey);
        gambardungeon.add(gambarPidgey);
        gambarPidgey.setVisible(false);
        gambarPidgey.setBounds(800,215,1000,600);
        
        gambarBlastoise = new JLabel();
        ImageIcon blastoise = new ImageIcon("blastoise.png");
        ImageIcon scaledBlastoise = new ImageIcon(blastoise.getImage().getScaledInstance(310, 310, Image.SCALE_SMOOTH));
        gambarBlastoise.setIcon(scaledBlastoise);
        gambardungeon.add(gambarBlastoise);
        gambarBlastoise.setVisible(false);
        gambarBlastoise.setBounds(800,205,1000,600);
        
        gambarArticuno = new JLabel();
        ImageIcon articuno = new ImageIcon("articuno.png");
        ImageIcon scaledArticuno = new ImageIcon(articuno.getImage().getScaledInstance(480, 480, Image.SCALE_SMOOTH));
        gambarArticuno.setIcon(scaledArticuno);
        gambardungeon.add(gambarArticuno);
        gambarArticuno.setVisible(false);
        gambarArticuno.setBounds(800,100,1000,600);
        
        gambarGeodude = new JLabel();
        ImageIcon geodude = new ImageIcon("geodude.png");
        ImageIcon scaledGeodude = new ImageIcon(geodude.getImage().getScaledInstance(420, 370, Image.SCALE_SMOOTH));
        gambarGeodude.setIcon(scaledGeodude);
        gambardungeon.add(gambarGeodude);
        gambarGeodude.setVisible(true);
        gambarGeodude.setBounds(780,260,1000,600);
        
        // Add Components to Frame
        frame.add(battleLog);
        frame.add(controlPanel, BorderLayout.PAGE_END);
        frame.add(homebase, BorderLayout.PAGE_START);
        frame.add(gambardungeon);
        
        // Add Action Listeners
        healButton.addActionListener(e -> healMonsters());
        exploreButton.addActionListener(e -> exploreDungeon());
//        saveButton.addActionListener(e -> saveProgress(players));
        addButton.addActionListener(e -> addNewMonsterToCollection());
        viewButton.addActionListener(e -> viewAllMonsters());
        quitButton.addActionListener(e -> System.exit(0));

        // Battle button listeners
        attackButton.addActionListener(e -> performBasicAttack());
        specialAttackButton.addActionListener(e -> performSpecialAttack());
        elementalAttackButton.addActionListener(e -> performElementalAttack());
        useItemButton.addActionListener(e -> useItem());
        fleeButton.addActionListener(e -> flee());

        // Initialize game elements
        dungeon = new Dungeon();

        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }
        player = new Player(playerName);

        frame.setVisible(false);
        showOpeningFrame();
    }
    
    private void showOpeningFrame() {
        JFrame openingFrame = new JFrame("Welcome");
        openingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openingFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        openingFrame.setUndecorated(true); // Hapus border dan title bar

        // Dapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        JLayeredPane layeredPane = new JLayeredPane();
        openingFrame.add(layeredPane, BorderLayout.CENTER);
        layeredPane.setSize(screenWidth, screenHeight);

        // Muat dan scale GIF
        ImageIcon opening = new ImageIcon("battle.gif");
        Image openingGif = opening.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledOpening = new ImageIcon(openingGif);
        JLabel gifLabel = new JLabel(scaledOpening);
        gifLabel.setBounds(0, 0, screenWidth, screenHeight);
        layeredPane.add(gifLabel, JLayeredPane.DEFAULT_LAYER);

        // Panel untuk tombol
        JPanel openingPanel = new JPanel();
        openingPanel.setLayout(new GridLayout(1, 2, 10, 10));
        openingPanel.setOpaque(false); // Membuat panel transparan
        openingPanel.setBounds(590, 500, 200, 30);

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
        loginButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setPreferredSize(new Dimension(100, 50));
        loginButton.setBackground(Color.WHITE);
        exitButton.setBackground(Color.WHITE);

        openingPanel.add(loginButton);
        openingPanel.add(exitButton);

        layeredPane.add(openingPanel, JLayeredPane.PALETTE_LAYER);

        // Muat dan scale judul
        ImageIcon judul = new ImageIcon("judulbaru.png");
        Image judull = judul.getImage().getScaledInstance(600, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledJudul = new ImageIcon(judull);
        JLabel judulLabel = new JLabel(scaledJudul);
        judulLabel.setBounds(375, 140, 600, 450);
        layeredPane.add(judulLabel, JLayeredPane.PALETTE_LAYER);

        loginButton.addActionListener(e -> {
            playerName = JOptionPane.showInputDialog("Input player name!");
            openingFrame.dispose();
            frame.setVisible(true);
        });

        exitButton.addActionListener(e -> System.exit(0));

        openingFrame.setVisible(true);
    }

    private void healMonsters() {
        player.healAllMonsters();
        showMessage("All monsters have been healed.");
    }

    private void exploreDungeon() {
        wildMonster = dungeon.generateWildMonster();
        if (wildMonster != null) {
            showMessage("A wild " + wildMonster.getName() + " appeared!");
            currentMonster = player.getMonsters().get(0);
            startBattle();
        } else {
            showMessage("No wild monsters found.");
        }
        homebase.setVisible(false);
        gambardungeon.setVisible(true);
//        gambarplayer.setVisible(true);
    }
    
    private void viewAllMonsters() {
    List<Monster> monsters = player.getMonsters();
        if (monsters.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No monsters in your collection.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Monsters in your collection:\n");
            for (Monster monster : monsters) {
                message.append(monster.getName()).append(" (Level: ").append(monster.getLevel())
                        .append(", HP: ").append(monster.getHp()).append(", EP: ").append(monster.getEp()).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString(), "Your Monsters", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private void addNewMonsterToCollection() {
        if (availableMonsters.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No available monsters to add.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create an array of available monster names
        String[] monsterNames = new String[availableMonsters.size()];
        for (int i = 0; i < availableMonsters.size(); i++) {
            Monster monster = availableMonsters.get(i);
            monsterNames[i] = monster.getName() + " (Element: " + monster.getElement() + ")";
        }

        // Show a dialog to select a monster to add
        String selectedMonsterName = (String) JOptionPane.showInputDialog(
                frame,
                "Choose a monster to add to your collection:",
                "Add New Monster",
                JOptionPane.PLAIN_MESSAGE,
                null,
                monsterNames,
                monsterNames[0]
        );

        if (selectedMonsterName != null) {
            // Find the selected monster
            Monster selectedMonster = null;
            for (Monster monster : availableMonsters) {
                if (selectedMonsterName.startsWith(monster.getName())) {
                    selectedMonster = monster;
                    break;
                }
            }

            if (selectedMonster != null) {
                try {
                    player.addMonster(selectedMonster);
                    availableMonsters.remove(selectedMonster); // Remove the selected monster from available monsters
                    refreshMonsterList();
                    outputArea.append(selectedMonster.getName() + " has been added to your collection.\n");
                } catch (CustomException e) {
                    JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void startBattle() {
        setBattleButtonsEnabled(true);
        updateBattleLog("Battle starts between " + currentMonster.getName() + " and " + wildMonster.getName() + "\n");
    }

    private void performBasicAttack() {
        if (wildMonster.getHp() > 0) {
            currentMonster.basicAttack(wildMonster);
            updateBattleLog(currentMonster.getName() + " uses Basic Attack on " + wildMonster.getName() + ". " + wildMonster.getName() + " HP is now " + wildMonster.getHp() + "\n");
            if (wildMonster.getHp() > 0) {
                wildMonster.basicAttack(currentMonster);
                updateBattleLog(wildMonster.getName() + " uses Basic Attack on " + currentMonster.getName() + ". " + currentMonster.getName() + " HP is now " + currentMonster.getHp() + "\n");
                if (currentMonster.getHp() <= 0) {
                    updateBattleLog(currentMonster.getName() + " has fainted!\n");
                    setBattleButtonsEnabled(false);
                }
            } else {
                updateBattleLog(wildMonster.getName() + " has fainted!\n");
                setBattleButtonsEnabled(false);
            }
        }
    }

    private void performSpecialAttack() {
        try {
            if (wildMonster.getHp() > 0) {
                currentMonster.specialAttack(wildMonster);
                updateBattleLog(currentMonster.getName() + " uses Special Attack on " + wildMonster.getName() + ". " + wildMonster.getName() + " HP is now " + wildMonster.getHp() + "\n");
                if (wildMonster.getHp() > 0) {
                    wildMonster.basicAttack(currentMonster);
                    updateBattleLog(wildMonster.getName() + " uses Basic Attack on " + currentMonster.getName() + ". " + currentMonster.getName() + " HP is now " + currentMonster.getHp() + "\n");
                    if (currentMonster.getHp() <= 0) {
                        updateBattleLog(currentMonster.getName() + " has fainted!\n");
                        setBattleButtonsEnabled(false);
                    }
                } else {
                    updateBattleLog(wildMonster.getName() + " has fainted!\n");
                    setBattleButtonsEnabled(false);
                }
            }
        } catch (CustomException e) {
            showMessage(e.getMessage());
        }
    }

    private void performElementalAttack() {
        if (wildMonster.getHp() > 0) {
            currentMonster.elementalAttack(wildMonster);
            updateBattleLog(currentMonster.getName() + " uses Elemental Attack on " + wildMonster.getName() + ". " + wildMonster.getName() + " HP is now " + wildMonster.getHp() + "\n");
            if (wildMonster.getHp() > 0) {
                wildMonster.basicAttack(currentMonster);
                updateBattleLog(wildMonster.getName() + " uses Basic Attack on " + currentMonster.getName() + ". " + currentMonster.getName() + " HP is now " + currentMonster.getHp() + "\n");
                if (currentMonster.getHp() <= 0) {
                    updateBattleLog(currentMonster.getName() + " has fainted!\n");
                    setBattleButtonsEnabled(false);
                }
            } else {
                updateBattleLog(wildMonster.getName() + " has fainted!\n");
                setBattleButtonsEnabled(false);
            }
        }
    }

    private void useItem() {
        String[] items = {"Health Potion", "Elemental Potion"};
        String item = (String) JOptionPane.showInputDialog(frame, "Choose an item to use:", "Use Item", JOptionPane.QUESTION_MESSAGE, null, items, items[0]);
        if (item != null) {
            currentMonster.useItem(item);
            updateBattleLog(currentMonster.getName() + " uses " + item + ". " + currentMonster.getName() + " HP is now " + currentMonster.getHp() + "\n");
        }
    }

    private void flee() {
        updateBattleLog(currentMonster.getName() + " fled the battle!\n");
        setBattleButtonsEnabled(false);
    }

    private void saveProgress(Map<String, Player> players) {
        Player.saveProgress(players);
        showMessage("Game progress saved.");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private void updateBattleLog(String log) {
        battleLog.append(log);
    }

    private void setBattleButtonsEnabled(boolean enabled) {
        attackButton.setEnabled(enabled);
        specialAttackButton.setEnabled(enabled);
        elementalAttackButton.setEnabled(enabled);
        useItemButton.setEnabled(enabled);
        fleeButton.setEnabled(enabled);
    }

    private void refreshMonsterList() {
        monsterListModel.clear();
        for (Monster monster : player.getMonsters()) {
            monsterListModel.addElement(monster.getName() + " (" + monster.getElement() + ")");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokemonBattleGUI::new);
    }
}