package model;

import java.util.ArrayList;

public class PlacePlantCommand implements Command{
	private Board board;
	private int row;
	private int col;
	private Plant p;
    //objects on board plants on board zombies on board.
    //levelPlants levelZombies
    private GridObject[][]		previousGridState;
    private ArrayList<GridObject> previousGridObjects;
    private ArrayList<Plant> 	previousPlantsOnBoard;
    private int 				previousCoins;
    
    private GridObject[][] 		nextGridState;
    private ArrayList<GridObject> nextGridObjects;
    private ArrayList<Plant> 	nextPlantsOnBoard;
    private int					nextCoins;
    
    /**
     * This method saves the previous and next values of the board before
     * a place plant command is executed
     * @param board
     * @param p
     * @param row
     * @param col
     */
    public PlacePlantCommand(Board board, Plant p, int row, int col) {
    	this.board = board;
    	this.row = row;
    	this.col = col;
    	this.p = p;
    	previousGridObjects = new ArrayList<GridObject>();
    	previousPlantsOnBoard = new ArrayList<Plant>();
    	nextGridObjects = new ArrayList<GridObject>();
    	nextPlantsOnBoard = new ArrayList<Plant>();
    	
        previousGridState = new GridObject[Board.GRID_HEIGHT][Board.GRID_WIDTH];
        nextGridState = new GridObject[Board.GRID_HEIGHT][Board.GRID_WIDTH];
        for (int i = 0; i < Board.GRID_HEIGHT; i++) {
        	for (int j = 0; j < Board.GRID_WIDTH; j++) {
        		GridObject o = board.grid[i][j];
        		previousGridState[i][j] = o;
        		nextGridState[i][j] = o;
        	}
        }
        nextGridState[row][col] = p;
        
        for (GridObject object: board.gridObjects) {
        	previousGridObjects.add(object);
        	nextGridObjects.add(object);
        	if (object instanceof Plant) {
	        	previousPlantsOnBoard.add((Plant)object);
	        	nextPlantsOnBoard.add((Plant)object);
        	}
        }
        nextPlantsOnBoard.add(p);
        nextGridObjects.add(p);
        
        previousCoins = Level.coins;
        nextCoins = Level.coins - p.getPrice();
    }
    
    /**
     * This method executes a place plant command.
     */
    public void execute() {
    	board.placePlant(p,col,row);
    }
    
    /**
     * This method undoes a place plant command.
     */
    public void undo() {
    	p.setCurrentTime(0);
    	board.grid = previousGridState;
    	board.gridObjects = previousGridObjects;
    	board.plantsOnBoard = previousPlantsOnBoard;
    	Level.coins = previousCoins;
    }
    
    /**
     * This method redoes a place plant command.
     */
    public void redo() {
    	p.setCurrentTime(p.fullTime);
    	board.grid = nextGridState;
    	board.gridObjects = nextGridObjects;
    	board.plantsOnBoard = nextPlantsOnBoard;
    	Level.coins = nextCoins;
    }
}
