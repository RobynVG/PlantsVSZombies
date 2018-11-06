package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	
	//Buttons(Plant VS Zombies Grid)
	private JPanel gridLayoutButtons;
	public static JButton[][] buttons; // A Button Array that retains the Buttons
	
	//Buttons(Plant Choices)
	private JPanel plantLayout;
	private JButton[] plantButtons;
	

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

		//Buttons For Main Board Game
		gridLayoutButtons = new JPanel(new GridLayout(GRID_HEIGHT,GRID_WIDTH));
		buttons = new JButton[GRID_HEIGHT][GRID_WIDTH];
		//Putting the buttons in the 2-D Array 
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				JButton button = new JButton("");
				button.setActionCommand(Integer.toString(i+1)+ Integer.toString(j));
				buttons[i][j] = new JButton("");
				buttons[i][j].setEnabled(true);
				gridLayoutButtons.add(buttons[i][j]);
			}
		}
		//Adding Buttons to the main Panel
		add(gridLayoutButtons,BorderLayout.CENTER);
		
		//Plant Layout that is scrollable.
		plantLayout = new JPanel(new GridLayout(8,1));
		
		//Buttons for Plant shops 
		plantButtons = new JButton[8];
		//Putting button into array
		for(int i = 0; i < plantButtons.length; i++) {
			plantButtons[i] = new JButton("");
			plantButtons[i].setEnabled(true);
			plantLayout.add(plantButtons[i]);	
		}
		//Adding Plant Panel to Main Panel
		add(new JScrollPane(plantLayout),BorderLayout.WEST);
		
		
		//Setting the minimum size of the main frame
		setMinimumSize(new Dimension(1000,500));
		//To Center the code
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
}
