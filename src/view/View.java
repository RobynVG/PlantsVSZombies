package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

import model.GridObject;
import model.Plant;
import model.Zombie;

public class View extends JFrame {

	public static final int GRID_HEIGHT = 4;// The Height of the board.
	public static final int GRID_WIDTH = 7;// The Width of the board.

	// Menu
	private JMenuBar menuBar;// The menu bar
	private JMenu menu; // The menu
	private JMenuItem start; // Menu item to start the game
	private JMenuItem restart; // Menu item to restart the game.
	private JMenuItem help; // Menu item for game help
	private JTextArea output;
	private JFrame InfoFrame;
	private JLabel coins;
	
	private JButton lastTurn, endTurn, nextTurn;
	// Buttons(Plant VS Zombies Grid)
	private JPanel gridLayoutButtons;
	public static JButton[][] buttons; // A Button Array that retains the Buttons
	private JLabel j1,j2,j3,j4;
	
	// Plant choices
	private JList<JPanel> menuList;

	/**
	 * Constructor to construct the view.
	 */
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
				buttons[i][j].setEnabled(false);
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

		coins = new JLabel("       Sun Points: 10");
		coins.setPreferredSize(new Dimension(30,30));
		JPanel plantsAndCoins = new JPanel();
		plantsAndCoins.setLayout(new BorderLayout());
		
		plantsAndCoins.add(menuList, BorderLayout.NORTH);
		plantsAndCoins.add(coins, BorderLayout.SOUTH);
		
		add(plantsAndCoins, BorderLayout.WEST);
		
		
		JPanel optionsPanel = new JPanel();
		
		lastTurn = new JButton("Undo");
		endTurn = new JButton("End Turn");
		nextTurn = new JButton("Redo");
		lastTurn.setEnabled(false);
		endTurn.setEnabled(true);
		nextTurn.setEnabled(false);
		
		optionsPanel.add(lastTurn);
		optionsPanel.add(endTurn);
		optionsPanel.add(nextTurn);
		
		add(optionsPanel, BorderLayout.SOUTH);
		//add(menuList, BorderLayout.WEST);

		
		
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
		//calls the information frame method
		 makeInfoFrame();
	}
	
	/**
	 * This method is the information frame that displays the plants prices.
	 */
	public void makeInfoFrame()
	{
		this.InfoFrame = new JFrame("INFO");
		this.InfoFrame.setSize(230,90);
		this.InfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.InfoFrame.setResizable(false);
		
		JPanel Panel = new JPanel(new BorderLayout());
		GridLayout gl = new GridLayout(3, 19);
		JPanel InfoPanel = new JPanel(gl);
		
		JLabel j1 = new JLabel("VenusFlyTrap : ");
		j1.setForeground(Color.GREEN); 
		InfoPanel.add(j1); 
		
		JLabel j2 = new JLabel("5 coins");
		j2.setForeground(Color.CYAN);
		InfoPanel.add(j2);
		
		JLabel j3 = new JLabel("SunFlower : ");
		j3.setForeground(Color.green); 
		InfoPanel.add(j3); 
		
		JLabel j4 = new JLabel("2 coins");
		j4.setForeground(Color.CYAN);
		InfoPanel.add(j4);
		
		Panel.add(InfoPanel, BorderLayout.CENTER);
		InfoPanel.setOpaque(true);
		InfoPanel.setBackground(Color.DARK_GRAY);
		
		this.InfoFrame.add(Panel); //adding the buddy panel to the buddy frame
		this.InfoFrame.setLocation(30, 30);
		this.InfoFrame.setVisible(true);
	}
	
	public void displayStats(GridObject o) {
		if (o instanceof Plant)
			displayPlantStats((Plant) o);
		else
			displayZombieStats((Zombie) o);
		
//		JDialog dialog = new JDialog();
//		dialog.setTitle("Stats");
//		JLabel typeLabel = new JLabel("Type:     " + o.getObjectTitle());
//		JLabel healthLabel = new JLabel("Health:     " +ABORT )
//		dialog.add(typeLabel);
//		
//		dialog.setSize(200,300);
//		dialog.setVisible(true);
	}
	
	private void displayPlantStats(Plant plant) {
		JDialog dialog = new JDialog();
		dialog.setTitle("Plant Stats");
		dialog.setLayout(new GridLayout(0,2));
		
		dialog.add(new JLabel("Plant Type:"));
		dialog.add(new JLabel(plant.getObjectTitle()));
		dialog.add(new JLabel("Health:"));
		dialog.add(new JLabel(""+plant.getHealth() + "/" + plant.getFullHealth()));
		dialog.add(new JLabel("Strength:"));
		dialog.add(new JLabel(""+plant.getStrength()));
		dialog.add(new JLabel("Wait Period:"));
		dialog.add(new JLabel(""+plant.getCurrentTime() + "/" + plant.getFullTime() + " Turns"));
		
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	private void displayZombieStats(Zombie zombie) {
		JDialog dialog = new JDialog();
		dialog.setTitle("Zombie Stats");
		dialog.setLayout(new GridLayout(0,2));

		dialog.add(new JLabel("Zombie Type:"));
		dialog.add(new JLabel(zombie.getObjectTitle()));
		dialog.add(new JLabel("Health:"));
		dialog.add(new JLabel(""+zombie.getHealth()+ "/" + zombie.getFullHealth()));
		dialog.add(new JLabel("Strength:"));
		dialog.add(new JLabel(""+zombie.getStrength()));
		
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	
	/**
	 * This method gets the buttons
	 * @return JButtons
	 */
	public JButton[][] getButtons() {
		return buttons;
	}

	/**
	 * This method gets the list of plants that are stored.
	 * @return menuList
	 */
	public JList<JPanel> getPlants() {
		return menuList;
	}
	
	/**
	 * This method gets JLabel that displays the coins.
	 * @return coins
	 */
	public JLabel getCoins() {
		return coins;
	}
	
	/**
	 * This method gets the menu item help.
	 * @return help
	 */
	public JMenuItem getHelp() {
		return help;
	}
	
	public JButton getLastTurn() {
		return lastTurn;
	}

	public void setLastTurn(JButton lastTurn) {
		this.lastTurn = lastTurn;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}

	public JButton getNextTurn() {
		return nextTurn;
	}

	public void setNextTurn(JButton nextTurn) {
		this.nextTurn = nextTurn;
	}

}