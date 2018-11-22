package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import controller.Controller.State;

public class Board {
	public static final int GRID_HEIGHT = 4;
	public static final int GRID_WIDTH = 7;
	public static final String GRID_X[] = { "A", "B", "C", "D", "E", "F", "G" }; // Must be same length as GRID_WIDTH
	public static final String GRID_Y[] = { "1", "2", "3", "4" };
	
	public GridObject[][] grid;
	public ArrayList<GridObject> gridObjects = new ArrayList<GridObject>();
	public ArrayList<Zombie> zombiesOnBoard = new ArrayList<Zombie>();
	public ArrayList<Plant> plantsOnBoard = new ArrayList<Plant>();
	public CommandManager commandManager = new CommandManager();
	
	
	/**
	 * This method sets up and prints the grid.
	 */
	public void setupGrid() {
		grid = new GridObject[GRID_HEIGHT][GRID_WIDTH];
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				grid[i][j] = new NullSpace();
			}
		}
	}
	
	/**
	 * This method spawns the zombies on the board.
	 */
	public void spawnZombies() {
		if (Level.getAllZombies().isEmpty())
			return;
		
		int yPos = ThreadLocalRandom.current().nextInt(0, 3);
		int randZombie = ThreadLocalRandom.current().nextInt(0, Level.getAllZombies().size());
		Zombie zombie = Level.getAllZombies().remove(randZombie); 

		if (isEmpty(yPos, Board.GRID_WIDTH - 1))
			placeZombie(zombie, Board.GRID_WIDTH - 1, yPos);
	}
	
	public void startBoardTurn() {
		commandManager.executeCommand(new BoardTurnCommand(this));
	}
	
	public void boardTurn() {
		//All plants then all zombies on the board - Advance or attack
		if (!zombiesOnBoard.isEmpty()) {
			for (Plant plant : plantsOnBoard)
				plant.go(this);
			
			for (Zombie zombie : zombiesOnBoard)			
				zombie.go(this);
		}
		removeTheDead();
		//Spawn
		spawnZombies();

		//Give player coins reduce count down on plant timers
		prepareNextTurn();
	}
	
	/**
	 * This method prepares for the upcomming turn
	 */
	public void prepareNextTurn() {
		for (Plant plant: plantsOnBoard) {
			if (plant instanceof SunFlower)
				Level.coins = Level.coins + SunFlower.COIN_BONUS;
		}
		for (Plant plant: Level.allPlants) {
			plant.newTurn();
		}
	}

	/**
	 * This method checks if there is any zombies in the first column.
	 * 
	 * @return A boolean, true is there is any zombies in the first column otherwise
	 *         false.
	 */
	public boolean zombiesInFirstColumn() {
		for (int i = 0; i < GRID_Y.length; i++) {
			if (getObject(i, 0) instanceof Zombie) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method places a plant on the grid.
	 * 
	 * @param plant (Plant), a plant.
	 * @param posY  (int), the y-coordinate of the grid.
	 * @param posX  (int), the x-coordinate of the grid.
	 */
	public void placePlant(Plant plant, int posX, int posY) {
		grid[posX][posY] = plant;
		plantsOnBoard.add(plant);
		gridObjects.add(plant);
		Level.coins -= plant.getPrice();
		plant.setCurrentTime(plant.getFullTime());
	}

	/**
	 * This method places a zombie on the grid.
	 * 
	 * @param zombie (Zombie), a zombie.
	 * @param posY   (int), the y-coordinate of the grid.
	 * @param posX   (int), the x-coordinate of the grid.
	 */
	public void placeZombie(Zombie zombie, int posY, int posX) {
		grid[posX][posY] = zombie;
		zombiesOnBoard.add(zombie);
		gridObjects.add(zombie);
	}

	/**
	 * This method gets the object to the left.
	 * 
	 * @param zombie (Zombie), a zombie.
	 * @return gridOject (GridObject), an object on the grid.
	 */
	public GridObject toTheLeft(GridObject zombie) { // mostly just called by zombies but no need to specify
		int j = getX(zombie);
		int i = getY(zombie);
		if ((i != -1 || j != -1) && j!=0)
			return (grid[i][j - 1]);
		return null;
	}

	/**
	 * This method gets the object to the right.
	 * 
	 * @param plant (Plant), a plant.
	 * @return gridObject (GridObject), an object on the grid.
	 */
	public GridObject toTheRight(GridObject plant) {
		int j = getX(plant);
		int i = getY(plant);
		if ((i != -1 || j != -1) && j!= Board.GRID_WIDTH-1) {
			return grid[i][j + 1];
		}
		return null;
	}

	/**
	 * This method moves a grid object on the board.
	 * 
	 * @param gridObject (GridObject), the object on the grid.
	 * @param nullSpace  (NullSpace), empty space.
	 */
	public void move(GridObject gridObject, NullSpace nullSpace) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		int jnext = getX(nullSpace);
		int inext = getY(nullSpace);
		grid[inext][jnext] = gridObject;
		grid[i][j] = nullSpace;
	}

	/**
	 * This method moves a grid object on the board.
	 * 
	 * @param gridObject (GridObject), the object on the grid.
	 * @param nullSpace  (NullSpace), empty space.
	 */
	public boolean remove(GridObject gridObject) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		grid[i][j] = new NullSpace();
		
		gridObjects.remove(gridObject);
		if (gridObject instanceof Zombie )
			zombiesOnBoard.remove(gridObject);
		if (gridObject instanceof Plant)
			plantsOnBoard.remove(gridObject);
		
		if(grid[i][j] instanceof NullSpace) {
			return true;
		}
		return false;
	}

	/**
	 * This method gets the x-coordinate of the gridObject.
	 * 
	 * @param gridObject (Grid Object), the object on the grid.
	 * @return A int, the x-coordinate of the gridObject.
	 */
	private int getX(GridObject gridObject) {
		int i, j = 0;
		for (i = 0; i < GRID_HEIGHT; i++) {
			for (j = 0; j < GRID_WIDTH; j++) {
				if (grid[i][j].equals(gridObject))
					return j;
			}
		}
		return -1;
	}

	/**
	 * This method gets the y-coordinate of the gridObject.
	 * 
	 * @param gridObject (GridObject), the object on the grid.
	 * @return A int, the y-coordinate of the gridObject.
	 */
	private int getY(GridObject gridObject) {
		int i, j = 0;
		for (i = 0; i < GRID_HEIGHT; i++) {
			for (j = 0; j < GRID_WIDTH; j++) {
				if (grid[i][j].equals(gridObject))
					return i;
			}
		}
		return -1;
	}
	
	private void removeTheDead() {
		Iterator<Plant> iteratorP = plantsOnBoard.iterator();
		Iterator<Zombie> iteratorZ = zombiesOnBoard.iterator();
		while(iteratorP.hasNext())
		{ 
			Plant p = iteratorP.next();
			if (p.getHealth() <= 0) {
				iteratorP.remove();
				remove(p);
			}
		}
		while(iteratorZ.hasNext())
		{
			Zombie z = iteratorZ.next();
			
			if (z.getHealth() <= 0) {
				iteratorZ.remove();
				remove(z);
			}
		}
	} 	


	/**
	 * This method checks if a location from the grid is empty.
	 * 
	 * @param posY (int), the y-coordinate on the grid.
	 * @param posX (int), the x-coordinate on the grid.
	 * @return A boolean, true if the position is empty otherwise false.
	 */
	public boolean isEmpty(int posY, int posX) {
		return (getObject(posY, posX) instanceof NullSpace);
	}

	/**
	 * This method gets the object that is stored in the grid.
	 * 
	 * @param i (int), this is the x coordinate of the grid.
	 * @param j (int), this is the y coordinate of the grid.
	 * @return A GridObject, the item on the grid.
	 */
	public GridObject getObject(int i, int j) {
		return grid[i][j];
	}
	
	public ArrayList<GridObject> getGridObjects() {
		return gridObjects;
	}

	public void setGridObjects(ArrayList<GridObject> gridObjects) {
		this.gridObjects = gridObjects;
	}

	public ArrayList<Zombie> getZombiesOnBoard() {
		return zombiesOnBoard;
	}

	public void setZombiesOnBoard(ArrayList<Zombie> zombiesOnBoard) {
		this.zombiesOnBoard = zombiesOnBoard;
	}

	public ArrayList<Plant> getPlantsOnBoard() {
		return plantsOnBoard;
	}

	public void setPlantsOnBoard(ArrayList<Plant> plantsOnBoard) {
		this.plantsOnBoard = plantsOnBoard;
	}

	
	//Used for debugging
	private void printGrid(GridObject[][] objects) {
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				if (objects[i][j] instanceof GenericZombie)
					System.out.print("[ g ]");
				else if (objects[i][j] instanceof SunFlower)
					System.out.print("[ S ]");
				else if (objects[i][j] instanceof VenusFlyTrap)
					System.out.print("[ V ]");
				else
					System.out.print("[   ]");
			}
			System.out.print("\n");
		}	
	}

}