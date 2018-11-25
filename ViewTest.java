import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import org.junit.Test;

public class ViewTest extends JFrame {

	private JPanel gridLayoutButtons;
	public static JButton[][] buttons;
	public static final int GRID_HEIGHT = 4;// The Height of the board.
	public static final int GRID_WIDTH = 7;// The Width of the board.

	private JMenuBar menuBar;// The menu bar
	private JMenu menu; // The menu
	private JMenuItem start; // Menu item to start the game
	private JMenuItem restart; // Menu item to restart the game.
	private JMenuItem help; // Menu item for game help
	private JTextArea output;
	private JFrame InfoFrame;
	
	private JLabel j1;
	private JLabel j2;
	private JLabel j3;
	private JLabel j4;
	
	
	
	@Test
	public void testView() {
		
		
				// MenuBar for storing the Menu
				menuBar = new JMenuBar();
				menu = new JMenu("Menu");
				assertNotNull(menuBar);
				
				
				start = new JMenuItem("Start"); // Start Menu Item, to first start the game.
				restart = new JMenuItem("Restart"); // Restart Menu Item, reset the game.
				help = new JMenuItem("Help"); // Help Menu item, instructions for the user if they need help.
				assertNotNull(start);
				assertNotNull(restart);
				assertNotNull(help);
				
				menu.add(start);
				menu.add(restart);
				menu.add(help);
				
				menuBar.add(menu);
				assertEquals(menuBar.getMenu(0).isEnabled(), true);
				output = new JTextArea(); 
				assertNotNull(output);
				output.setBackground(Color.LIGHT_GRAY);
				assertEquals(output.getBackground(),Color.LIGHT_GRAY);
				output.setLayout(null);
				assertNull(output.getLayout());
				
				buttons = new JButton[GRID_HEIGHT][GRID_WIDTH];
				assertNotNull(buttons);
				// Buttons For Main Board Game
				gridLayoutButtons = new JPanel(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
				assertNotNull(gridLayoutButtons);
				
				
				JPanel sunflowerPanel = new JPanel();
				JPanel venusflytrapPanel = new JPanel();
				assertNotNull(sunflowerPanel);
				assertNotNull(venusflytrapPanel);
				
				setResizable(false);
				assertEquals(isResizable(),false);
				
		
		
	}
	@Test
	public void testMakeInfoFrame() {
		
		
		this.InfoFrame = new JFrame("INFO");
		this.InfoFrame.setSize(230,90);
		this.InfoFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel Panel = new JPanel(new BorderLayout());
		GridLayout gl = new GridLayout(3, 19);
		JPanel InfoPanel = new JPanel(gl);
		assertNotNull(InfoPanel);
		assertNotNull(Panel);
		
		
		JLabel j1 = new JLabel("VenusFlyTrap : ");
		j1.setForeground(Color.GREEN); 
		InfoPanel.add(j1); 
		assertNotNull(j1);
		
		JLabel j2 = new JLabel("3 coins");
		j2.setForeground(Color.CYAN);
		InfoPanel.add(j2);
		assertNotNull(j2);
		
		JLabel j3 = new JLabel("SunFlower : ");
		j3.setForeground(Color.green); 
		InfoPanel.add(j3); 
		assertNotNull(j3);
		
		JLabel j4 = new JLabel("2 coins");
		j4.setForeground(Color.CYAN);
		InfoPanel.add(j4);
		assertNotNull(j4);
		
		Panel.add(InfoPanel, BorderLayout.CENTER);
		InfoPanel.setOpaque(true);
		InfoPanel.setBackground(Color.DARK_GRAY);
		assertTrue(InfoPanel.isOpaque());
		assertEquals(InfoPanel.getBackground(),Color.DARK_GRAY);
		
		
		InfoFrame.add(Panel);
		InfoFrame.setVisible(true);
		assertTrue(InfoFrame.isVisible());
	}
}
