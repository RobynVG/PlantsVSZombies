package controller;

import model.Level;
import model.NullSpace;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

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

	public Controller(View view) {
		this.view = view;
		startGame();
		Board.setupGrid();
	}

	/**
	 * Initialize GUI's Action Listeners
	 */
	public void initController() {
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
			}
		}

	}
	
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

//		while (true) {
//			playerTurn(row, col);
//			addDelay(500);
//			Board.boardTurn();
//			addDelay(2000);
//			Board.prepareNewTurn();
//			playerWinLose();
//		}
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
	
	private static void updateButton(JButton button, GridObject o) {
		if (o instanceof NullSpace) {
			button.setIcon(null);
			return;
		}
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
	
	private static void updateView() {
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				updateButton(view.getButtons()[i][j], Board.grid[i][j]);
				if (Board.grid[i][j]!=null)
					view.getButtons()[i][i].setEnabled(false);
				else
					view.getButtons()[i][i].setEnabled(true);
			}
		}
	}
	

}
