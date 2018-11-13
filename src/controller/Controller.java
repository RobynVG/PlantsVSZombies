package controller;

import model.Level;
import model.NullSpace;
import model.Plant;
import model.SunFlower;
import model.VenusFlyTrap;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
						gridCond(false);
						String s = e.getActionCommand();
						System.out.println(s);
						String[] rowcol = s.split(" ");
						JLabel j = (JLabel) view.getPlants().getSelectedValue().getComponent(0);
						addPlant(j.getText(), Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]));
						Board.boardTurn();
						gridCond(false);
					}
				});
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


	private static void updateButton(JButton button, GridObject o) {
		if (o instanceof NullSpace) {
			button.setIcon(null);
			return;
		}

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

	private static void gridCond(boolean gridEnabled) {
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				updateButton(view.getButtons()[i][j], Board.grid[i][j]);
				if (!Board.isEmpty(i,j) || j == NUMOFCOLS - 1)
					view.getButtons()[i][j].setEnabled(false);
				else
					view.getButtons()[i][j].setEnabled(gridEnabled);
			}
		}
	}

//	private static void gridCond(boolean cond) {
//		for (int i = 0; i < NUMOFROWS; i++) {
//			for (int j = 0; j < NUMOFCOLS; j++) {
//				view.getButtons()[i][j].setEnabled(cond);
//			}
//		}
//	}
}
