package controller;

import model.Level;
import model.NullSpace;
<<<<<<< HEAD
import model.Plant;
import model.SunFlower;
import model.VenusFlyTrap;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
=======

import java.awt.Dimension;
import java.awt.Image;
>>>>>>> origin/master
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
<<<<<<< HEAD
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
=======
>>>>>>> origin/master

import model.Board;
import model.GridObject;
import view.View;

public class Controller {
	static Scanner reader = new Scanner(System.in);
	static View view;
	static int row;
	static int col;
	static final int NUMOFROWS = 4;
	static final int NUMOFCOLS = 7;
<<<<<<< HEAD
	public boolean pickedPlant = false;
=======
>>>>>>> origin/master

	public Controller(View view) {
		this.view = view;
		startGame();
<<<<<<< HEAD
=======
		Board.setupGrid();
>>>>>>> origin/master
	}

	/**
	 * Initialize GUI's Action Listeners
	 */
	public void initController() {
<<<<<<< HEAD
		gridCond(false);
		view.getPlants().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					gridCond(true);
				}
			}
		});
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				view.getButtons()[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String s = e.getActionCommand();
						System.out.println(s);
						String[] rowcol = s.split(" ");
						JLabel j = (JLabel) view.getPlants().getSelectedValue().getComponent(0);
						addPlant(j.getText(), Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]));
						Board.boardTurn();
						updateView();
						gridCond(false);
					}
				});
=======
		for (row = 0; row < NUMOFROWS; row++) {
			for (col = 0; col < NUMOFCOLS; col++) {
				// add action listeners to all grid buttons
				System.out.println(row);
				View.buttons[row][col].addActionListener(e -> playerTurn(row, col));
				if (Board.isEmpty(col, row)) {
					buttonsEnable(true);
				}
				else {
					buttonsEnable(false);
				}
				disableGrid();
>>>>>>> origin/master
			}
		}

	}
<<<<<<< HEAD

=======
	
>>>>>>> origin/master
	/**
	 * Enable Buttons
	 */
	public static void buttonsEnable(boolean cond) {
		View.buttons[row][col].setEnabled(cond);
	}

	/**
	 * This method starts the game for Plant Versus Zombies.
	 */
	public static void startGame() {
		Level.level1();
		Board.setupGrid();
<<<<<<< HEAD
=======

//		while (true) {
//			playerTurn(row, col);
//			addDelay(500);
//			Board.boardTurn();
//			addDelay(2000);
//			Board.prepareNewTurn();
//			playerWinLose();
//		}
>>>>>>> origin/master
	}

	/**
	 * This method asks if the player wants to restart the game.
	 */
	public static void reStart() {
		char reply;
		do {
			System.out.println("Would you like to Re-Start the Level? y/n");
			reply = reader.next().charAt(0);
		} while (reply != 'y' && reply != 'n');

		if (reply == 'y') {
			startGame();
		} else {
			System.out.println("GAME OVER----------------------------------------------------");
			System.exit(0);
		}
	}

	/*
	 * 
	 * Adds a Delay
	 */
	private static void addDelay(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Player wins level
	 * 
	 * @True if the player wins the level
	 */
	public static void playerWinLose() {
		// If Player Wins the Level
		if (Level.isEmpty() && !Board.zombiesLeft()) {
			System.out.print("WON LEVEL!----------------------------------------------------------" + "\n");
			reStart();
			return;
		}
		// If Player Loses the Level
		if (Board.zombiesInFirstColumn()) {
			System.out.println("LOST LEVEL!-------------------------------------------------------" + "\n");
			reStart();
			return;
		}
		return;
	}

	/**
	 * This method allows the player to have a turn.
	 */
<<<<<<< HEAD
	public static void startTurn(String plantName) {

		if (!(Board.plantAffordable())) {
			System.out.println("Cannot Afford Plants");
			return;
		}

	}

	public static void addPlant(String plantName, int i, int j) {
		Plant p = null;
		if (plantName.equals("SunFlower")) {
			p = new SunFlower();
			Board.placePlant(p, j, i);
			return;
		} else if (plantName.equals("VenusFlyTrap")) {
			p = new VenusFlyTrap();
			Board.placePlant(p, j, i);
			return;
		}
	}

=======
	public static void playerTurn(int i, int j) {
		while (true) {
			
			if (!(Board.plantAffordable())) {
				addDelay(1500);
				break;
			}
			addPlant(i, j);
		}
	}

	public static void addPlant(int i, int j) {
		System.out.println("You may only place your plant on an available space. Please try again.");
	}
	
>>>>>>> origin/master
	private static void updateButton(JButton button, GridObject o) {
		if (o instanceof NullSpace) {
			button.setIcon(null);
			return;
		}
<<<<<<< HEAD
		if (o instanceof VenusFlyTrap) {
			System.out.println("Venus");
		}
		System.out.println(button.getSize());
		Dimension d = button.getSize();
		try {
			ImageIcon image = new ImageIcon(new ImageIcon("resources/" + o.getImageTitle()).getImage()
					.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
			button.setIcon(image);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		view.revalidate();
		view.repaint();
	}

=======
		System.out.println(button.getSize());
		Dimension d = button.getSize();
		try {
			 Image img = ImageIO.read(view.getClass().getResource("/" + o.getImageTitle()));
			 Image newimg = img.getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH );
			 button.setIcon(new ImageIcon(newimg));
		 } catch (Exception ex) {
			 System.out.println(ex);
		 }
		 view.revalidate();
		 view.repaint();
	}
	
>>>>>>> origin/master
	private static void updateView() {
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				updateButton(view.getButtons()[i][j], Board.grid[i][j]);
<<<<<<< HEAD
				if (Board.grid[i][j] != null || j == NUMOFCOLS - 1)
=======
				if (Board.grid[i][j]!=null || j == NUMOFCOLS - 1)
>>>>>>> origin/master
					view.getButtons()[i][j].setEnabled(false);
				else
					view.getButtons()[i][j].setEnabled(true);
			}
		}
	}
<<<<<<< HEAD

	private static void gridCond(boolean cond) {
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				view.getButtons()[i][j].setEnabled(cond);
			}
		}
	}
=======
	
	private static void disableGrid() {
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				view.getButtons()[i][j].setEnabled(false);
			}
		}
	}
	
>>>>>>> origin/master

}
