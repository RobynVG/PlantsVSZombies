
import java.util.ArrayList;

public class Board {
	public static final int GRID_HEIGHT = 4;
	public static final int GRID_WIDTH = 7;
	public static final String GRID_X[] = { "A", "B", "C", "D", "E", "F", "G" }; // Must be same length as GRID_WIDTH
	public static final String GRID_Y[] = { "1", "2", "3", "4" };
	public static GridObject[][] grid;
	public static ArrayList<GridObject> gridObjects = new ArrayList<GridObject>();

	public Board() {
		setupGrid();
	}

	/*
	 * This method checks if there are any zombies left on the board
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

	/*
	 * This method checks if there are any Zombies in the first column
	 */
	public static boolean endLevel() {
		for (int i = 0; i < GRID_Y.length; i++) {
			if (getObject(i, 0) instanceof Zombie) {
				return true;
			}
		}
		return false;
	}

	/*
	 * This method returns the index at which the given character (its really a
	 * string) is at
	 * 
	 * @Param String c, a single valued string
	 * 
	 * @Return the index at which the String c was found first.
	 */
	public static int getGridX(String c) {
		for (int i = 0; i < GRID_X.length; i++) {
			if (c.equals(GRID_X[i])) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * This method returns the index at which the given character (its really a
	 * string) is at
	 * 
	 * @Param String c, a single valued string
	 * 
	 * @Return the index at which the String c was found first.
	 */
	public static int getGridY(String c) {
		for (int i = 0; i < GRID_Y.length; i++) {
			if (c.equals(GRID_Y[i])) {
				return i;
			}
		}
		return -1;
	}

	public static void setupGrid() {
		grid = new GridObject[GRID_HEIGHT][GRID_WIDTH];

		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				grid[i][j] = new NullSpace();
			}
		}
		printGrid();
	}

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

	public static void placePlant(Plant plant, int posY, int posX) {
		grid[posX][posY] = plant;
		gridObjects.add(plant);
		printGrid();
	}

	public static void placeZombie(Zombie zombie, int posY, int posX) {
		grid[posX][posY] = zombie;
		gridObjects.add(zombie);
		printGrid();
	}

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

	public static GridObject toTheRight(GridObject plant) {
		int j = getX(plant);
		int i = getY(plant);
		if(i != -1 || j != -1) {
			return grid[i][j+1];
		}
		return null;
	}

	public static void move(GridObject gridObject, NullSpace nullSpace) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		int jnext = getX(nullSpace);
		int inext = getY(nullSpace);
		grid[inext][jnext] = gridObject;
		grid[i][j] = nullSpace;
	}

	public static void remove(GridObject gridObject) {
		int j = getX(gridObject);
		int i = getY(gridObject);
		grid[i][j] = new NullSpace();
		gridObjects.remove(gridObject);
	}

	/*
	 * Removes the plant at specified Column and Row
	 * 
	 * @Param Column the plant is in
	 * 
	 * @Param Row the plant is in
	 * 
	 * @Return True if plant is removed, false otherwise
	 */
	public static boolean removePlant(int col, int row) {
		grid[col][row] = new NullSpace();
		if(grid[col][row] instanceof NullSpace) {
			return true;
		}
		return false;
	}

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

	public static boolean isEmpty(int posY, int posX) {
		return (getObject(posX, posY) instanceof NullSpace);
	}

	private static GridObject getObject(int i, int j) {
		return grid[i][j];
	}
	
	public static boolean isPlant(int posY, int posX) {
		if(grid[posY][posX] instanceof Plant) {
			return true;
		}
		return false;
	}
}
