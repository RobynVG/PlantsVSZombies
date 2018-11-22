package controller;

import model.Level;
import model.NullSpace;
import model.Plant;
import model.SunFlower;
import model.VenusFlyTrap;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import model.Board;
import model.GridObject;
import model.GridObjectFactory;
import view.View;

public class Controller {
	static Scanner reader = new Scanner(System.in);
	private View view;
	//public static final int GRID_HEIGHT = 4;
	//public static final int GRID_WIDTH = 7;
	
	//static final int Board.GRID_HEIGHT = 4;
	//static final int Board.GRID_WIDTH = 7;
	private State gridState;
	
	public enum State {
		POSITIONS,
		STATS,
		DISABLED;
	}
	
	
	public Controller(View view) {
		this.view = view;
		startGame();
		Board.setupGrid();
	}
	
	/**
	 * This method starts the game for Plant Versus Zombies.
	 */
	public static void startGame() {
		//Initialize the level and grid
		Level.level1();
		Board.setupGrid();
	}
	
	/**
	 * Initialize GUI's Action Listeners
	 */
	public void initController() {
		//Initialize action listener for the help tab to generate information panel
		view.getHelp().addActionListener(e -> spawnInfoFrame());
		//Initialize action listener for all plant buttons
		view.getPlants().addListSelectionListener(e -> plantSelected(e));
		
		//Initialize action listener for all of the grid buttons
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++)
				view.getButtons()[i][j].addActionListener(e -> gridPositionSelected(e));
		}
		//Initialize action listener for the end turn button
		view.getEndTurn().addActionListener(e -> endTurn());
		
		view.getUndoTurn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Board.commandManager.undo();
				gridCond(State.STATS);
			}
		});
		
		view.getRedoTurn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Board.commandManager.redo();
				gridCond(State.STATS);
			}
		});
	}
	
	private void plantSelected(ListSelectionEvent arg0) {
		//If statements deals with multiple events fired by one click
		if (arg0.getValueIsAdjusting() || view.getPlants().getSelectedValue() == null)
			return;
		//Extract the name of the plant from the list item selected
		String plantName = ((JLabel) view.getPlants().getSelectedValue().getComponent(0)).getText();
		
		//For all of the plants available in current level
		for (Plant plant: Level.allPlants) {
			//Continue for loop until we find an instance of plant selected
			if (plantName != plant.getObjectTitle())
				continue;
			else {
				//If the plant is not affordable notify the user, disable the grid, and clear the selection
				if (!plant.isAvailable()) {
					JOptionPane.showMessageDialog(null,"This plant is available in " + plant.getCurrentTime() + " turn(s)");
					gridCond(State.STATS);
					view.getPlants().clearSelection();
					return;
				}
				//If the plant is not affordable notify the user, disable the grid, and clear the selection
				if (!plant.isAffordable()) {
					JOptionPane.showMessageDialog(null,"You cannot afford this plant");
					gridCond(State.STATS);
					view.getPlants().clearSelection();
					return;
				}
				//If this statement is reached the user has chosen a valid plant. Enable the grid so it can be placed
				gridCond(State.POSITIONS);
			}	
		}		
	}
	
	private void gridPositionSelected(ActionEvent e) {
		String s = e.getActionCommand();
		String[] rowcol = s.split(" ");
				
		int i = Integer.parseInt(rowcol[0]);
		int j = Integer.parseInt(rowcol[1]);
		
		if (gridState == State.STATS) {
			GridObject selected = Board.getObject(i, j);
			view.displayStats(selected);
			return;
		}
			
			
		String plantSelected = ((JLabel) view.getPlants().getSelectedValue().getComponent(0)).getText();
	
		//Immediately disable the grid once player has selected where to place their plant
		gridCond(State.DISABLED);
		//Disable the flower buttons, the player must wait until the board turn has ended
		flowerButtonsEnabled(false);
		//Clear the plant list selection so once enabled the user can select a new plant
		view.getPlants().clearSelection();
		//Add the plant to the board
		Board.placePlant((Plant)GridObjectFactory.createNewGridObject(plantSelected), Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]));
		//addPlant(plantSelected, Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]));
		//Display coins
		view.getCoins().setText("       Sun Points: " + Level.coins);
		//Allow player to check current stats of any object
		gridCond(State.STATS);
		//Enable the flower buttons in case player would like to plant enother plant
		flowerButtonsEnabled(true);
	}

	private void endTurn() {
		for(;;) {
			//Plants and zombies attack then zombies spawn
			Board.startBoardTurn();
			//Update the coins on the GUI
			view.getCoins().setText("       Sun Points: " + Level.coins);
			//Update the grid
			gridCond(State.DISABLED);
			//Check if a win or loss has occured
			playerWinLose();
			//If no plant is affordable player must wait for the board to perform another turn
			//until they accumulate enough sun points to go
			if(Level.plantAffordable())
				break;
		}
		//Board turn has ended, allow the player to pick another plant
		flowerButtonsEnabled(true);
		gridCond(State.STATS);
	}

	
	//Enable or disable all of the plant buttons
	private void flowerButtonsEnabled(boolean enabled) {
		view.getPlants().setEnabled(enabled);	
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
	private void playerWinLose() {
		// If Player Wins the Level because there are no zombies to be spawned an no zombies on the board
		if (Level.isEmpty() && Board.zombiesOnBoard.isEmpty()) {
			//Spawn a dialog to inform user
			JOptionPane.showMessageDialog(null, "!!!!!!!YOU WON!!!!!!!!");
			//Dispose of the GUI. Game has ended
			view.dispose();
			System.exit(0);
			return;
		}
		// If Player Loses the Level because zombies have reached the first column
		if (Board.zombiesInFirstColumn()) {
			//Spawn a dialog to inform user
			JOptionPane.showMessageDialog(null, "You Lost....");
			//Dispose of the GUI. Game has ended
			view.dispose();
			System.exit(0);
			return;
		}
		return;
	}

	/**
	 * This method adds a plant to the board
	 */
	public static void addPlant(String plantName, int i, int j) {
		Plant p = null;
		//Create a plant based on the string parameter
		if (plantName.equals("SunFlower")) {
			p = new SunFlower();
			//place the plant
			Board.placePlant(p, j, i);
			return;
		} else if (plantName.equals("VenusFlyTrap")) {
			p = new VenusFlyTrap();
			//place the plant
			Board.placePlant(p, j, i);
			return;
		}
	}


	private static void updateButton(JButton button, GridObject o) {
		//If button is to display a nullspce the button is cleared (null space has no image)
		if (o instanceof NullSpace) {
			button.setIcon(null);
			return;
		}
		try {
			//Get the image icon corresponding to the name of the object parameter
			ImageIcon image = new ImageIcon(new ImageIcon("resources/" + o.getObjectTitle()+".png").getImage()
					.getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING));
			//Set the icon on the board
			button.setIcon(image);
			//Set the disable icon. This ensure the icon is not greyed out when it is disabled
			button.setDisabledIcon(image);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	private void spawnInfoFrame() {
		view.makeInfoFrame();
	}
	
	//Refresh the board and set the unnocupied buttons to enabled or disabled according to the parameter passed
	private void gridCond(State state) {
		gridState = state;
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++) {
				JButton button = view.getButtons()[i][j];
				//Update the btton at the specified location
				updateButton(view.getButtons()[i][j], Board.grid[i][j]);
				
				switch(state) {
				case STATS:
					if (!Board.isEmpty(i,j))
						button.setEnabled(true);
					else
						button.setEnabled(false);
					break;	
				case POSITIONS:
					if (!Board.isEmpty(i, j) || j == Board.GRID_WIDTH - 1)
						button.setEnabled(false);
					else
						button.setEnabled(true);
					break;
				case DISABLED:
					button.setEnabled(false);
					break;
				}
			}
		}
		view.getCoins().setText("       Sun Points: " + Level.coins);
		
		view.getUndoTurn().setEnabled(Board.commandManager.isUndoAvailable());
		view.getRedoTurn().setEnabled(Board.commandManager.isRedoAvailable());
		
		//Refresh the GUI
		view.revalidate();
		view.repaint();
	}
}
