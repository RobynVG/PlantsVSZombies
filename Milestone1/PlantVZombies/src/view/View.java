package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.*;

//TODO: JList Each Plant will have a setActionCommand so you know what was choose, Info Button with 
public class View extends JFrame {

	public static final int GRID_HEIGHT = 4;// The Height of the board.
	public static final int GRID_WIDTH = 7;// The Width of the board.

	// Menu
	private JMenuBar menuBar;// The menu bar
	private JMenu menu; // The menu
	private JMenuItem start; // Menu item to start the game
	private JMenuItem restart; // Menu item to restart the game.
	private JMenuItem help; // Menu item for game help

	// Buttons(Plant VS Zombies Grid)
	private JPanel gridLayoutButtons;
	public static JButton[][] buttons; // A Button Array that retains the Buttons

	// Plant choices
	private JList<JPanel> menuList;

	public View() {
		// Sets the main layout as a BorderLayout.
		setLayout(new BorderLayout());
		// MenuBar for storing the Menu
		menuBar = new JMenuBar();

		// Menu
		menu = new JMenu("Menu");

		// Menu Item
		start = new JMenuItem("Start"); // Start Menu Item, to first start the game.
		restart = new JMenuItem("Restart"); // Restart Menu Item, reset the game.
		help = new JMenuItem("Help"); // Help Menu item, instructions for the user if they need help.

		// Adding Menu item to the Menu
		menu.add(start);
		menu.add(restart);
		menu.add(help);

		// Adding Menu to Menu Bar
		menuBar.add(menu);

		// Adding Menu Bar
		add(menuBar, BorderLayout.NORTH);

		// Buttons For Main Board Game
		gridLayoutButtons = new JPanel(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
		buttons = new JButton[GRID_HEIGHT][GRID_WIDTH];
		// Putting the buttons in the 2-D Array
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setEnabled(true);
				buttons[i][j].setActionCommand(i + " " + j);
				gridLayoutButtons.add(buttons[i][j]);
			}
		}
		// Adding Buttons to the main Panel
		add(gridLayoutButtons, BorderLayout.CENTER);

		menuList = new JList<JPanel>();
		menuList.setCellRenderer(new ImageListCellRenderer());

		// Plant icons, get images
		ImageIcon sunflowerImage = new ImageIcon(
				new ImageIcon("resources/SunFlower.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon venusflytrapImage = new ImageIcon(
				new ImageIcon("resources/VenusFlyTrap.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		// add images to the labels
		JLabel sunflowerLabel = new JLabel("SunFlower", sunflowerImage, JLabel.LEFT);
		JLabel venusflytrapLabel = new JLabel("VenusFlyTrap", venusflytrapImage, JLabel.LEFT);

		// create panels for each plant
		JPanel sunflowerPanel = new JPanel();
		JPanel venusflytrapPanel = new JPanel();

		// add JLabel to JPanel
		sunflowerPanel.add(sunflowerLabel);
		venusflytrapPanel.add(venusflytrapLabel);

		// array of panels
		JPanel[] panels = { sunflowerPanel, venusflytrapPanel };

		// JList of panels
		menuList.setListData(panels);

		menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuList.setLayoutOrientation(JList.VERTICAL);

		add(menuList, BorderLayout.WEST);

		// Setting the minimum size of the main frame
		setMinimumSize(new Dimension(1000, 500));
		// To Center the code
		setLocationRelativeTo(null);
		// Allows the user to resize the Frame by minimizing and maximizing.
		setResizable(false);
		// Allows the user to exit out of the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Packs Everything in to the GUI.
		pack();
		// Sets the title name to Plants vs Zombies
		setTitle("Plants VS Zombies");
		// Allows the GUI to be visible.
		setVisible(true);
	}

	public JButton[][] getButtons() {
		return buttons;
	}

	public JList<JPanel> getPlants() {
		return menuList;
	}
}