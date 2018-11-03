
import java.util.ArrayList;

public class Board {
	public static final int GRID_HEIGHT = 4;
	public static final int GRID_WIDTH = 7;
	public static final String GRID_X[] = { "A", "B", "C", "D", "E", "F", "G" }; // Must be same length as GRID_WIDTH
	public static final String GRID_Y[] = { "1", "2", "3", "4" };
	public static GridObject[][] grid;
	public static ArrayList<GridObject> gridObjects = new ArrayList<GridObject>();
	
	/**
	 * This constructs a grid.
	 */
	public Board() {
		setupGrid();
	}

	/**
	 * This method checks if there is any zombies left on the board
	 * @return A boolean, true if there is zombies left otherwise false.
	 */
	public boolean zombiesLeft() {
		for (GridObject[] g : grid) {
			for (GridObject obj : g) {
				if (obj instanceof Zombie) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * This method checks if there is any zombies in the first column.
	 * @return A boolean, true is there is any zombies in the first column otherwise false.  
	 */
	public static boolean endLevel() {
		for (int i = 0; i < GRID_Y.length; i++) {
			if (getObject(i, 0) instanceof Zombie) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method determines the equivalent x-coordinate integer of the x-coordinate character.
	 * @param c (String), a single valued string.
	 * @return A (int), the index at which c was found first.
	 */
	public static int getGridX(String c) {
		for (int i = 0; i < GRID_X.length; i++) {
			if (c.equals(GRID_X[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method determines the equivalent y-coordinate integer of the y-coordinate character.
	 * @param c (String), a single valued string.
	 * @return A (int), the index at which c was found first.
	 */
	public static int getGridY(String c) {
		for (int i = 0; i < GRID_Y.length; i++) {
			if (c.equals(GRID_Y[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method sets up and prints the grid.
	 */
	public static void setupGrid() {
		grid = new GridObject[GRID_HEIGHT][GRID_WIDTH];

		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				grid[i][j] = new NullSpace();
			}
		}
		printGrid();
	}

	/**
	 * This method prints out the grid.
	 */
	public static void printGrid() {
		System.out.print("   ");
		for (String s : GRID_X) {
			System.out.print(s + "    ");
		}
		System.out.println();
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				if (j == 0)
					System.out.print(i + 1);
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * This method places a plant on the grid.
	 * @param plant (Plant), a plant.
	 * @param posY (int), the y-coordinate of the grid.
	 * @param posX (int), the x-coordinate of the grid.
	 */
	public static void placePlant(Plant plant, int posY, int posX) {
		grid[posX][posY] = plant;
		gridObjects.add(plant);
		printGrid();
	}

	/**
	 * This method places a zombie on the grid.
	 * @param zombie (Zombie), a zombie. 
	 * @param posY (int), the y-coordinate of the grid.
	 * @param posX (int), the x-coordinate of the grid.
	 */
	public static void placeZombie(Zombie zombie, int posY, int posX) {
		grid[posX][posY] = zombie;
		gridObjects.add(zombie);
		printGrid();
	}

	/**
	 * This method gets the object to the left.
	 * @param zombie (Zombie), a zombie.
	 * @return gridOject (GridObject), an object on the grid.
	 */
	public static GridObject toTheLeft(GridObject zombie) { // mostly just called by zombies but no need to specify
		int j = getX(zombie);
		if (j - 1 == -1) // Beginning of board, Plants LOSE! Also returning null is not the same as
							// NullSpace
			return null;
		int i = getY(zombie);
		try {
			return (grid[i][j - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds in To the left int i = " + i + "  j = " + j);
			printGrid();
			System.exit(1);
		}
		return null;
	}

	/**
	 * This method gets the object to the right.
	 * @param plant (Plant), a plant.
	 * @return gridObject (GridObject), an object on the grid.
	 */
	public static GridObject toTheRight(GridObject plant) {
		int j = getX(plant);
		int i = getY(plant);
		if(i != -1 || j != -1) {
			return grid[i][j+1];
		}
		return null;
	}


	/**
	 * This method moves a grid object on the board.
	 * @param gridObject (GridObject), the object on the grid.
	 * @param nullSpace (NullSpace), empty space.
	 */
	public static void move(GridObject gridObject, NullSpace nullSpace) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		int jnext = getX(nullSpace);
		int inext = getY(nullSpace);
		grid[inext][jnext] = gridObject;
		grid[i][j] = nullSpace;
	}

	/**
	 * This method moves a grid object on the board.
	 * @param gridObject (GridObject), the object on the grid.
	 * @param nullSpace (NullSpace), empty space.
	 */
	public static void remove(GridObject gridObject) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		grid[i][j] = new NullSpace();
		gridObjects.remove(gridObject);
	}

	/**
	 * This method removes the plant on the grid.
	 * @param col (int), y-coordinate.
	 * @param row (int), x-coordinate.
	 * @return A boolean, true if the plant was removed otherwise false.
	 */
	public static boolean removePlant(int col, int row) {
		grid[col][row] = new NullSpace();
		if(grid[col][row] instanceof NullSpace) {
			return true;
		}
		return false;
	}

	/**
	 * This method gets the x-coordinate of the gridObject.
	 * @param gridObject (Grid Object), the object on the grid.
	 * @return A int, the x-coordinate of the gridObject.
	 */
	private static int getX(GridObject gridObject) {
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
     * @param gridObject (GridObject), the object on the grid.
     * @return A int, the y-coordinate of the gridObject.
     */
	private static int getY(GridObject gridObject) {
		int i, j = 0;
		for (i = 0; i < GRID_HEIGHT; i++) {
			for (j = 0; j < GRID_WIDTH; j++) {
				if (grid[i][j].equals(gridObject))
					return i;
			}
		}
		return -1;
	}

	/**
	 * This method checks if a location from the grid is empty.
	 * @param posY (int), the y-coordinate on the grid.
	 * @param posX (int), the x-coordinate on the grid.
	 * @return A boolean, true if the position is empty otherwise false.
	 */
	public static boolean isEmpty(int posY, int posX) {
		return (getObject(posX, posY) instanceof NullSpace);
	}

	/**
	 * This method gets the object that is stored in the grid.
	 * @param i (int), this is the x coordinate of the grid.
	 * @param j (int), this is the y coordinate of the grid. 
	 * @return A GridObject, the item on the grid.
	 */
	private static GridObject getObject(int i, int j) {
		return grid[i][j];
	}
	
	/**
	 * This method checks if the object on the grid is a plant.
	 * @param posY (int), the y-coordinate.
	 * @param posX (int), the x-coordinate.
	 * @return A boolean, true if the object is a plant otherwise false.
	 */
	public static boolean isPlant(int posY, int posX) {
		if(grid[posY][posX] instanceof Plant) {
			return true;
		}
		return false;
	}
}