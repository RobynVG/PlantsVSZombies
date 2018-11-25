package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;

import model.Board;
import model.GridObject;
import model.NullSpace;
import model.Plant;
import model.Zombie;

public class View extends JFrame {

	// Menu
	private JMenuBar menuBar;// The menu bar
	private JMenu menu; // The menu
	private JMenuItem start, restart, help; // Menu items

	private JLabel coins; // label to hold coin values

	private JButton undoTurn, endTurn, redoTurn;

	// Buttons(Plant VS Zombies Grid)
	private JPanel gridLayoutButtons;
	public static JButton[][] buttons; // A Button Array that retains the Buttons

	// Plant choices
	private JList<JPanel> menuList;

	// animationThread
	private Thread animationThread;

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
		// Initialize help menu item enabled only when plant information panel is not
		// already displayed.
		help.setEnabled(false);
		// Adding Menu items to the Menu
		menu.add(start);
		menu.add(restart);
		menu.add(help);
		// Adding Menu to Menu Bar
		menuBar.add(menu);
		// Adding Menu Bar
		add(menuBar, BorderLayout.NORTH);

		// Buttons For Main Board Game
		gridLayoutButtons = new JPanel(new GridLayout(Board.GRID_HEIGHT, Board.GRID_WIDTH));
		buttons = new JButton[Board.GRID_HEIGHT][Board.GRID_WIDTH];
		// Putting the buttons in the 2-D Array
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setEnabled(false);
				buttons[i][j].setActionCommand(i + " " + j);
				if ((i + j) % 2 == 0) {
					buttons[i][j].setBackground(new Color(154, 205, 50));
				} else {
					buttons[i][j].setBackground(new Color(173, 255, 47));
				}
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
		ImageIcon walnutImage = new ImageIcon(
				new ImageIcon("resources/Walnut.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon potatoeImage = new ImageIcon(
				new ImageIcon("resources/Potatoe.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon peaShooterImage = new ImageIcon(
				new ImageIcon("resources/PeaShooter.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		// add images to the labels
		JLabel sunflowerLabel = new JLabel("SunFlower", sunflowerImage, JLabel.LEFT);
		JLabel venusflytrapLabel = new JLabel("VenusFlyTrap", venusflytrapImage, JLabel.LEFT);
		JLabel walnutLabel = new JLabel("Walnut", walnutImage, JLabel.LEFT);
		JLabel potatoeLabel = new JLabel("Potatoe", potatoeImage, JLabel.LEFT);
		JLabel peaShooterLabel = new JLabel("PeaShooter", peaShooterImage, JLabel.LEFT);

		// create panels for each plant
		JPanel sunflowerPanel = new JPanel();
		JPanel venusflytrapPanel = new JPanel();
		JPanel walnutPanel = new JPanel();
		JPanel potatoePanel = new JPanel();
		JPanel peaShooterPanel = new JPanel();

		// add JLabel to JPanel
		sunflowerPanel.add(sunflowerLabel);
		venusflytrapPanel.add(venusflytrapLabel);
		walnutPanel.add(walnutLabel);
		potatoePanel.add(potatoeLabel);
		peaShooterPanel.add(peaShooterLabel);

		// array of panels
		JPanel[] panels = { sunflowerPanel, venusflytrapPanel, walnutPanel, potatoePanel, peaShooterPanel };

		// JList of panels
		menuList.setListData(panels);

		menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuList.setLayoutOrientation(JList.VERTICAL);

		coins = new JLabel("       Sun Points: 50");
		JPanel plantsAndCoins = new JPanel();
		plantsAndCoins.setLayout(new BorderLayout());

		plantsAndCoins.add(menuList, BorderLayout.NORTH);
		plantsAndCoins.add(coins, BorderLayout.SOUTH);

		add(plantsAndCoins, BorderLayout.WEST);

		JPanel optionsPanel = new JPanel();

		undoTurn = new JButton("Undo");
		endTurn = new JButton("End Turn");
		redoTurn = new JButton("Redo");
		undoTurn.setEnabled(false);
		endTurn.setEnabled(true);
		redoTurn.setEnabled(false);

		optionsPanel.add(undoTurn);
		optionsPanel.add(endTurn);
		optionsPanel.add(redoTurn);

		add(optionsPanel, BorderLayout.SOUTH);

		// Setting the minimum size of the main frame
		setMinimumSize(new Dimension(1000, 500));
		// Close to center but allow room for plant information panel.
		setLocation(210, 100);
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
		// calls the information frame method
		makeInfoFrame();
	}

	/**
	 * This method is the information frame that displays the plants prices.
	 */
	public void makeInfoFrame() {
		help.setEnabled(false);
		JDialog infoFrame = new JDialog(this);
		infoFrame.setTitle("Pricing Information");
		infoFrame.setResizable(false);

		JPanel Panel = new JPanel(new BorderLayout());
		GridLayout gl = new GridLayout(0, 2);
		JPanel InfoPanel = new JPanel(gl);

		// VenusFlyTrap Info
		JLabel j1 = new JLabel("VenusFlyTrap : ");
		j1.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j1.setForeground(Color.GREEN);
		InfoPanel.add(j1);

		JLabel j2 = new JLabel("150 coins");
		j2.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j2.setForeground(Color.CYAN);
		InfoPanel.add(j2);

		// SunFlower Info
		JLabel j3 = new JLabel("SunFlower : ");
		j3.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j3.setForeground(Color.green);
		InfoPanel.add(j3);

		JLabel j4 = new JLabel("50 coins");
		j4.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j4.setForeground(Color.CYAN);
		InfoPanel.add(j4);

		// Walnut Info
		JLabel j5 = new JLabel("Walnut : ");
		j5.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j5.setForeground(Color.green);
		InfoPanel.add(j5);

		JLabel j6 = new JLabel("50 coins");
		j6.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j6.setForeground(Color.CYAN);
		InfoPanel.add(j6);

		// Potatoe Info
		JLabel j7 = new JLabel("Potatoe : ");
		j7.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j7.setForeground(Color.green);
		InfoPanel.add(j7);

		JLabel j8 = new JLabel("25 coins");
		j8.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j8.setForeground(Color.CYAN);
		InfoPanel.add(j8);

		// Potatoe Info
		JLabel j9 = new JLabel("Pea Shooter : ");
		j9.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j9.setForeground(Color.green);
		InfoPanel.add(j9);

		JLabel j10 = new JLabel("100 coins");
		j10.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
		j10.setForeground(Color.CYAN);
		InfoPanel.add(j10);

		Panel.add(InfoPanel, BorderLayout.CENTER);
		InfoPanel.setOpaque(true);
		InfoPanel.setBackground(Color.DARK_GRAY);

		infoFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				help.setEnabled(true);
			}
		});

		infoFrame.add(Panel); // adding the buddy panel to the buddy frame
		infoFrame.pack();
		infoFrame.setLocation(0, 100);
		infoFrame.setVisible(true);
	}

	public void displayStats(GridObject o) {
		if (o instanceof Plant)
			displayPlantStats((Plant) o);
		else
			displayZombieStats((Zombie) o);
	}

	private void displayPlantStats(Plant plant) {
		JDialog dialog = new JDialog();
		dialog.setTitle("Plant Stats");
		dialog.setLayout(new GridLayout(0, 2));

		dialog.add(new JLabel("Plant Type:"));
		dialog.add(new JLabel(plant.getObjectTitle()));
		dialog.add(new JLabel("Health:"));
		dialog.add(new JLabel("" + plant.getHealth() + "/" + plant.getFullHealth()));
		dialog.add(new JLabel("Strength:"));
		dialog.add(new JLabel("" + plant.getStrength()));
		dialog.add(new JLabel("Wait Period:"));
		dialog.add(new JLabel("" + plant.getCurrentTime() + "/" + plant.getFullTime() + " Turns"));

		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private void displayZombieStats(Zombie zombie) {
		JDialog dialog = new JDialog();
		dialog.setTitle("Zombie Stats");
		dialog.setLayout(new GridLayout(0, 2));

		dialog.add(new JLabel("Zombie Type:"));
		dialog.add(new JLabel(zombie.getObjectTitle()));
		dialog.add(new JLabel("Health:"));
		dialog.add(new JLabel("" + zombie.getHealth() + "/" + zombie.getFullHealth()));
		dialog.add(new JLabel("Strength:"));
		dialog.add(new JLabel("" + zombie.getStrength()));

		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
	 * This method updates the button in the grid with its corresponding grid object
	 * 
	 * @param button
	 * @param o
	 */
	public void updateButton(JButton button, GridObject o) {

		// Get the image icon corresponding to the name of the object parameter (Frame
		// 0)
		ImageIcon frame1 = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle() + ".png").getImage()
				.getScaledInstance(80, 60, Image.SCALE_AREA_AVERAGING));

		// Animated Image (Frame 2)
		ImageIcon frame2 = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle() + "Animated1.png")
				.getImage().getScaledInstance(80, 60, Image.SCALE_AREA_AVERAGING));

		// Animated Image (Frame 3)
		ImageIcon frame3 = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle() + "Animated2.png")
				.getImage().getScaledInstance(80, 60, Image.SCALE_AREA_AVERAGING));

		// Animated Image (Frame 4)
		ImageIcon frame4 = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle() + "Animated3.png")
				.getImage().getScaledInstance(80, 60, Image.SCALE_AREA_AVERAGING));
		
		// Frames in Animation
		ArrayList<ImageIcon> frames = new ArrayList<ImageIcon>();
		frames.add(frame1);
		frames.add(frame2);
		frames.add(frame3);
		frames.add(frame4);

		button.setIcon(frame1);
		
		animationThread = new Thread(new Runnable() {

			@Override
			public void run() {

				// If button is to display a nullspce the button is cleared (null space has no
				// image)
				if (o instanceof NullSpace) {
					button.setIcon(null);
					return;
				}
				
				for (ImageIcon frame : frames) {
					button.setIcon(frame);
					addDelay(200);
				}
				
				button.setIcon(frame1);
				button.setDisabledIcon(frame1);
			}
		});
		playAnimation();
	}
	
	public void playAnimation() {
		animationThread.start();
	}

	/*
	 * Thread Delay
	 * 
	 * @param the delay Time for the thread
	 */
	private void addDelay(int delayTime) {
		try {
			Thread.sleep(delayTime);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * This method gets the buttons
	 * 
	 * @return JButtons
	 */
	public JButton[][] getButtons() {
		return buttons;
	}

	/**
	 * This method gets the list of plants that are stored.
	 * 
	 * @return menuList
	 */
	public JList<JPanel> getPlants() {
		return menuList;
	}

	/**
	 * This method gets JLabel that displays the coins.
	 * 
	 * @return coins
	 */
	public JLabel getCoins() {
		return coins;
	}

	/**
	 * This method gets the menu item help.
	 * 
	 * @return help
	 */
	public JMenuItem getHelp() {
		return help;
	}

	public JButton getUndoTurn() {
		return undoTurn;
	}

	public Thread getAnimationThread() {
		return animationThread;
	}

	public void setUndoTurn(JButton lastTurn) {
		this.undoTurn = lastTurn;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}

	public JButton getRedoTurn() {
		return redoTurn;
	}

	public void setRedoTurn(JButton nextTurn) {
		this.redoTurn = nextTurn;
	}

}