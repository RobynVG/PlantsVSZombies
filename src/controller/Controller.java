package controller;

import model.Level;
import model.NullSpace;

import model.PlacePlantCommand;

import model.PeaShooter;
import model.Plant;
import model.Potatoe;
import model.SunFlower;
import model.VenusFlyTrap;
import model.Walnut;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import model.Board;
import model.CommandManager;
import model.GridObject;
import model.GridObjectFactory;
import view.View;

public class Controller {
	static Scanner reader = new Scanner(System.in);
	private Board board;
	private View view;
	private CommandManager commandManager;
	private State gridState;
	private Level level;

	public enum State {
		POSITIONS, STATS, DISABLED;
	}

	/**
	 * The constructor, constructs the controller.
	 * @param board
	 * @param view
	 * @param cm
	 */
	public Controller(Board board, View view, CommandManager cm) {
		this.view = view;
		this.board = board;
		commandManager = cm;
		startGame();
	}

	/**
	 * This method starts the game for Plant Versus Zombies.
	 */
	public void startGame() {
		// Initialize the level and grid
		level = new Level(1);
		board.setLevel(level);
		board.setupGrid();
	}

	/**
	 * This method initialize GUI's Action Listeners
	 */
	public void initController() {
		// Initialize action listener for the help tab to generate information panel
		view.getHelp().addActionListener(e -> spawnInfoFrame());
		
		view.getImportOption().addActionListener(e -> importFromFile());
		
		view.getExportOption().addActionListener(e -> exportToFile());
		// Initialize action listener for all plant buttons
		view.getPlants().addListSelectionListener(e -> plantSelected(e));

		// Initialize action listener for all of the grid buttons
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++)
				view.getButtons()[i][j].addActionListener(e -> gridPositionSelected(e));
		}
		// Initialize action listener for the end turn button
		view.getEndTurn().addActionListener(e -> endTurn());

		// Initialize and define action listener for the undo button
		view.getUndoTurn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.commandManager.undo();
				gridCond(State.STATS);
			}
		});
		// Initialize and define action listener for the redo button
		view.getRedoTurn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.commandManager.redo();
				gridCond(State.STATS);
			}
		});
	}

	// Action listener for plant buttons
	private void plantSelected(ListSelectionEvent arg0) {
		// If statements deals with multiple events fired by one click
		if (arg0.getValueIsAdjusting() || view.getPlants().getSelectedValue() == null)
			return;
		// Extract the name of the plant from the list item selected
		String plantName = ((JLabel) view.getPlants().getSelectedValue().getComponent(0)).getText();

		// For all of the plants available in current level
		for (Plant plant : level.allPlants) {
			// Continue for loop until we find an instance of plant selected
			if (plantName != plant.getObjectTitle())
				continue;
			else {
				// If the plant is not affordable notify the user, disable the grid, and clear
				// the selection
				if (!plant.isAvailable()) {
					JOptionPane.showMessageDialog(null,
							"This plant is available in " + plant.getCurrentTime() + " turn(s)");
					gridCond(State.STATS);
					view.getPlants().clearSelection();
					return;
				}
				// If the plant is not affordable notify the user, disable the grid, and clear
				// the selection
				if (plant.getPrice() > level.coins) {
					JOptionPane.showMessageDialog(null, "You cannot afford this plant");
					gridCond(State.STATS);
					view.getPlants().clearSelection();
					return;
				}

				// If this statement is reached the user has chosen a valid plant. Enable the
				// grid so it can be placed
				gridCond(State.POSITIONS);
			}
		}
	}

	/**
	 * This method corresponds to the players selected position on the grid.
	 * @param e
	 */
	private void gridPositionSelected(ActionEvent e) {
		// Action command corresponds to i j
		String s = e.getActionCommand();
		String[] rowcol = s.split(" ");

		int i = Integer.parseInt(rowcol[0]);
		int j = Integer.parseInt(rowcol[1]);

		// If the grid is in the STATS state the player has selected an
		// area on the board to view an objects stats
		if (gridState == State.STATS) {
			// Get the grid object and display its stats
			GridObject selected = board.getObject(i, j);
			view.displayStats(selected);
			return;
		}

		String plantSelected = ((JLabel) view.getPlants().getSelectedValue().getComponent(0)).getText();

		// Immediately disable the grid once player has selected where to place their
		// plant
		gridCond(State.DISABLED);
		// Disable the flower buttons, the player must wait until the board turn has
		// ended
		plantButtonsEnabled(false);
		// Clear the plant list selection so once enabled the user can select a new
		// plant
		view.getPlants().clearSelection();
		// Add the plant to the board
		commandManager.executeCommand(
				new PlacePlantCommand(board, level, (Plant) GridObjectFactory.createNewGridObject(plantSelected),
						Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1])));
		// Display coins
		view.getCoins().setText("       Sun Points: " + level.coins);
		// Allow player to check current stats of any object
		gridCond(State.STATS);
		// Enable the flower buttons in case player would like to plant enother plant
		plantButtonsEnabled(true);
	}

	/**
	 * This method ends the turn.
	 */
	private void endTurn() {
		// Plants and zombies attack then zombies spawn
		board.startBoardTurn();
		// Update the coins on the GUI
		view.getCoins().setText("       Sun Points: " + level.coins);
		// Update the grid
		gridCond(State.DISABLED);
		// Check if a win or loss has occured
		playerWinLose();
		// If no plant is affordable the player is gifted coins.
		if (!level.plantAffordable()) {
			JOptionPane.showMessageDialog(view, "Wow you just found " + (50 - level.coins) + " Sun Points...");
			level.coins = 50;
		}
		
		
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++) {
				view.playAnimation(view.getButtons()[i][j], board.grid[i][j]);
			}
		}
		
		
		// Board turn has ended, allow the player to pick another plant
		plantButtonsEnabled(true);
		gridCond(State.STATS);
	}

	/**
	 * This method checks to see if the player has won or lost
	 */
	private void playerWinLose() {
		// If Player Wins the Level because there are no zombies to be spawned an no
		// zombies on the board
		if (level.zombiesEmpty() && board.zombiesOnBoard.isEmpty()) {
			// Spawn a dialog to inform user
			JOptionPane.showMessageDialog(null, "!!!!!!!YOU WON!!!!!!!!");
			// Dispose of the GUI. Game has ended
			view.dispose();
			System.exit(0);
			return;
		}
		// If Player Loses the Level because zombies have reached the first column
		if (board.zombiesInFirstColumn()) {
			// Spawn a dialog to inform user
			JOptionPane.showMessageDialog(null, "You Lost....");
			// Dispose of the GUI. Game has ended
			view.dispose();
			System.exit(0);
			return;
		}
		return;
	}

	/**
	 * This is the action listener for clicking on an object on the grid to view its
	 * stats. Spawns a dialog displaying stats.
	 */
	private void spawnInfoFrame() {
		view.makeInfoFrame();
	}

	/**
	 * This method refreshes the board and sets the unoccupied buttons to enabled or disabled
	 * according to the parameter passed.
	 * @param state
	 */
	private void gridCond(State state) {
		gridState = state;
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			for (int j = 0; j < Board.GRID_WIDTH; j++) {
				JButton button = view.getButtons()[i][j];
				// Update the btton at the specified location
				view.updateButton(view.getButtons()[i][j], board.grid[i][j]);

				switch (state) {
				case STATS:
					if (!board.isEmpty(i, j)) {
						button.setEnabled(true);
					} else
						button.setEnabled(false);
					break;
				case POSITIONS:
					if (!board.isEmpty(i, j) || j == Board.GRID_WIDTH - 1)
						button.setEnabled(false);
					else {
						button.setEnabled(true);
						button.setContentAreaFilled(true);
					}
					break;
				case DISABLED:
					button.setEnabled(false);
					button.setContentAreaFilled(false);
					;
					break;
				}
			}
		}
		view.getCoins().setText("       Sun Points: " + level.coins);

		view.getUndoTurn().setEnabled(board.commandManager.isUndoAvailable());
		view.getRedoTurn().setEnabled(board.commandManager.isRedoAvailable());

		// Refresh the GUI
		view.revalidate();
		view.repaint();
	}
	
	private void importFromFile() {
		Board boardIn = null;
		try {
	         FileInputStream fileIn = new FileInputStream("C:/Users/Robyn/Desktop/School Work/Fall 2018/SYSC 3110/OutputFile.txt");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         boardIn = (Board) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Board class not found");
	         c.printStackTrace();
	         return;
	      }
		System.out.print("\n");
		board.printGrid(boardIn.grid);
		board = boardIn;
		level = boardIn.getLevel();
		gridCond(State.DISABLED);
	}
	
	private void exportToFile() {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("C:/Users/Robyn/Desktop/School Work/Fall 2018/SYSC 3110/OutputFile.txt");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(board);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}

	/**
	 * This enables or disables all of the plant's buttons.
	 * @param enabled
	 */
	private void plantButtonsEnabled(boolean enabled) {
		view.getPlants().setEnabled(enabled);
	}
}