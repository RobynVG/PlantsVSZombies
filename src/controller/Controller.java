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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		view.getHelp().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				view.makeInfoFrame();
			}
		});
		

		gridCond(false);
		view.getPlants().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				if (!arg0.getValueIsAdjusting()) {
					if (view.getPlants().getSelectedValue()== null)
						return;
					JLabel j = (JLabel) view.getPlants().getSelectedValue().getComponent(0);
					for (Plant plant: Level.allPlants) {
						if (j.getText() != plant.getObjectTitle())
							continue;
						else {
							if (!plant.isAffordable()) {
								JOptionPane.showMessageDialog(null,"You cannot afford this plant");
								gridCond(false);
								view.getPlants().clearSelection();
								return;
							}
							if (!plant.isAvailable()) {
								JOptionPane.showMessageDialog(null,"This plant is available in " + plant.getCurrentTime() + " turn(s)");
								gridCond(false);
								view.getPlants().clearSelection();
								return;
							}
							
							gridCond(true);
							return;
						}	
					}	
				}
			}
		});
		
		for (int i = 0; i < NUMOFROWS; i++) {
			for (int j = 0; j < NUMOFCOLS; j++) {
				view.getButtons()[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						gridCond(false);
						flowerButtonsEnabled(false);
						String s = e.getActionCommand();
						String[] rowcol = s.split(" ");
						JLabel j = (JLabel) view.getPlants().getSelectedValue().getComponent(0);
						view.getPlants().clearSelection();
						addPlant(j.getText(), Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]));
						gridCond(false);
						//If player cannot buy plants, board turn restarts
						for(;;) {
							addDelay(500);
							boardTurn();
							view.getCoins().setText("Sun Points: " + Level.coins);
							playerWinLose();
							if(Level.plantAffordable())
								break;
						}
						flowerButtonsEnabled(true);
					}
				});
			}
		}
	}
	
	private static void boardTurn() {
		//Advance or attack
		Board.boardTurn();
		
		gridCond(false);
		//Spawn
		Board.spawnZombies();
		
		gridCond(false);
		
		Board.prepareNextTurn();
	}
	
	private static void flowerButtonsEnabled(boolean enabled) {
		view.getPlants().setEnabled(enabled);	
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
			//System.out.println("Would you like to Re-Start the Level? y/n");
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
		if (Level.isEmpty() && Board.zombiesOnBoard.isEmpty()) {
			JOptionPane.showMessageDialog(null, "!!!!!!!YOU WON!!!!!!!!");
			view.dispose();
			reStart();
			return;
		}
		// If Player Loses the Level
		if (Board.zombiesInFirstColumn()) {
			JOptionPane.showMessageDialog(null, "You Lost....");
			view.dispose();
			reStart();
			return;
		}
		return;
	}

	/**
	 * This method allows the player to have a turn.
	 */

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
		try {
			ImageIcon image = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle()+".png").getImage()
					.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
			button.setIcon(image);
			button.setDisabledIcon(image);
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
}
