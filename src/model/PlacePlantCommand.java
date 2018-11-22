package model;

import java.util.ArrayList;

public class PlacePlantCommand implements Command{
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
    
    public PlacePlantCommand(Plant p, int row, int col) {
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
        		GridObject o = Board.grid[i][j];
        		previousGridState[i][j] = o;
        		nextGridState[i][j] = o;
        	}
        }
        nextGridState[row][col] = p;
        
        for (GridObject object: Board.gridObjects) {
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
    	
    public void execute() {
    	Board.placePlant(p,col,row);
    }
    	
    public void undo() {
    	p.setCurrentTime(0);
    	Board.grid = previousGridState;
    	Board.gridObjects = previousGridObjects;
    	Board.plantsOnBoard = previousPlantsOnBoard;
    	Level.coins = previousCoins;
    }
    	
    public void redo() {
    	p.setCurrentTime(p.fullTime);
    	Board.grid = nextGridState;
    	Board.gridObjects = nextGridObjects;
    	Board.plantsOnBoard = nextPlantsOnBoard;
    	Level.coins = nextCoins;
    }
}
