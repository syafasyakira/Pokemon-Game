import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainGUI {
    private JFrame frame;
    private Player player;
    private JList<String> monsterList;
    private DefaultListModel<String> monsterListModel;
    private JTextArea outputArea;
    private List<Monster> availableMonsters;

    public MainGUI() {
        player = new Player("Ash");
        
        // Initialize the list of available monsters
        availableMonsters = new ArrayList<>(); 
        // You should populate this list with available monsters
        // For example:
        availableMonsters.add(new FireMonster("Charmander", 1, 100, 0));
        availableMonsters.add(new WaterMonster("Squirtle", 1, 100, 0));
        availableMonsters.add(new WindMonster("Pidgey", 1, 100, 0));
        availableMonsters.add(new IceMonster("Swinub", 1, 100, 0));
        availableMonsters.add(new EarthMonster("Geodude", 1, 100, 0));

        // Initialize the frame
        frame = new JFrame("Pokémon Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Setup layout
        frame.setLayout(new BorderLayout());

        // Setup the monster list
        monsterListModel = new DefaultListModel<>();
        monsterList = new JList<>(monsterListModel);
        JScrollPane monsterListScrollPane = new JScrollPane(monsterList);

        // Setup the output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));

        JButton addButton = new JButton("Add Monster");
        addButton.addActionListener(e -> addNewMonsterToCollection());

        JButton evolveButton = new JButton("Evolve Monster");
        evolveButton.addActionListener(e -> evolveMonster());

        JButton healButton = new JButton("Heal All Monsters");
        healButton.addActionListener(e -> healAllMonsters());

        JButton saveButton = new JButton("Save Progress");
//        saveButton.addActionListener(e -> saveProgress());

        JButton loadButton = new JButton("Load Progress");
//        loadButton.addActionListener(e -> loadProgress());

        JButton battleButton = new JButton("Start Battle");
        battleButton.addActionListener(e -> startBattle());

        buttonPanel.add(addButton);
        buttonPanel.add(evolveButton);
        buttonPanel.add(healButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(battleButton);

        // Add components to frame
        frame.add(monsterListScrollPane, BorderLayout.WEST);
        frame.add(outputScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.EAST);

        // Show frame
        frame.setVisible(true);
    }

    private void addNewMonsterToCollection() {
        JDialog dialog = new JDialog(frame, "Add New Monster", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Display available monsters
        JLabel label = new JLabel("Available monsters to add:");
        panel.add(label);

        JComboBox<String> monsterComboBox = new JComboBox<>();
        for (Monster monster : availableMonsters) {
            monsterComboBox.addItem(monster.getName() + " (Element: " + monster.getElement() + ")");
        }
        panel.add(monsterComboBox);

        JButton addButton = new JButton("Add to Collection");
        addButton.addActionListener(e -> {
            int choice = monsterComboBox.getSelectedIndex();
            if (choice >= 0 && choice < availableMonsters.size()) {
                Monster selectedMonster = availableMonsters.get(choice);
                try {
                    player.addMonster(selectedMonster);
                    availableMonsters.remove(selectedMonster); // Remove the selected monster from available monsters
                    refreshMonsterList();
                    outputArea.append(selectedMonster.getName() + " has been added to your collection.\n");
                    dialog.dispose();
                } catch (CustomException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(addButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void evolveMonster() {
        int selectedIndex = monsterList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a monster to evolve.");
            return;
        }

        String[] options = {"Fire", "Water", "Wind", "Ice", "Earth"};
        String newElement = (String) JOptionPane.showInputDialog(frame, "Choose new element:",
                "Evolve Monster", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        try {
            player.evolveMonster(selectedIndex, newElement);
            refreshMonsterList();
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void healAllMonsters() {
        player.healAllMonsters();
        outputArea.append("All monsters have been healed.\n");
    }

    private void saveProgress(Map<String, Player> players) {
        Player.saveProgress(players);
        outputArea.append("Progress saved successfully.\n");
    }
//
//    private void loadProgress() {
//        Player loadedPlayer = Player.loadProgress("player.sav");
//        if (loadedPlayer != null) {
//            player = loadedPlayer;
//            refreshMonsterList();
//            outputArea.append("Progress loaded successfully.\n");
//        } else {
//            outputArea.append("Failed to load progress.\n");
//        }
//    }

    private void startBattle() {
        int selectedIndex = monsterList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a monster to battle with.");
            return;
        }

        Monster playerMonster = player.getMonsters().get(selectedIndex);

        String opponentName = JOptionPane.showInputDialog(frame, "Enter Opponent Monster Name:");
        String[] options = {"Fire", "Water", "Wind", "Ice", "Earth"};
        String opponentType = (String) JOptionPane.showInputDialog(frame, "Choose Opponent Monster Type:",
                "Start Battle", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (opponentName != null && opponentType != null && !opponentName.isEmpty()) {
            Monster opponentMonster;
            switch (opponentType) {
                case "Fire":
                    opponentMonster = new FireMonster(opponentName, 1, 100, 0);
                    break;
                case "Water":
                    opponentMonster = new WaterMonster(opponentName, 1, 100, 0);
                    break;
                case "Wind":
                    opponentMonster = new WindMonster(opponentName, 1, 100, 0);
                    break;
                case "Ice":
                    opponentMonster = new IceMonster(opponentName, 1, 100, 0);
                    break;
                case "Earth":
                    opponentMonster = new EarthMonster(opponentName, 1, 100, 0);
                    break;
                default:
                    return;
            }

//            Battle battle = new Battle(player, playerMonster, opponentMonster);
//            battle.start();
            outputArea.append("Battle ended.\n");
        }
    }

    private void refreshMonsterList() {
        monsterListModel.clear();
        for (Monster monster : player.getMonsters()) {
            monsterListModel.addElement(monster.getName() + " (" + monster.getElement() + ")");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}