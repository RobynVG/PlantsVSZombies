import java.awt.Dimension;
import java.util.ArrayList;

public class GUI {
	public static final int GRID_HEIGHT = 4;
	public static final int GRID_WIDTH = 7;
	public static final String GRID_X[] = {"A","B","C","D","E","F","G"};  //Must be same length as GRID_WIDTH
	public static final String GRID_Y[] = {"1","2","3","4"};
	public static GridObject[][] grid;
	public static ArrayList<GridObject> gridObjects = new ArrayList<GridObject>();
	//public static ArrayList<Plant> boardPlant = new ArrayList<Plant>();
	//public static ArrayList<Zombie> boardZombie = new ArrayList<Zombie>();
	
	public GUI() {
		setupGrid();
	}
	
	public static void setupGrid() {
		grid = new GridObject[GRID_HEIGHT][GRID_WIDTH];
		
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				grid[i][j] = new NullSpace();
			}
		} printGrid();
	}
	
	public static void printGrid() {
		System.out.print("   ");
		for (String s: GRID_X) {
			System.out.print(s + "    ");
		}
		System.out.println();
		for (int i = 0; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				if (j==0)
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
	
//	public int getXPos(GridObject object) {
//		
//	}
//
//	public int getYPos(GridObject object) {
//	
//	}
	
	public static GridObject toTheLeft(GridObject zombie) { //mostly just called by zombies but no need to specify
		int j = getX(zombie);
		if (j - 1 == 0) //Beginning of board, Plants LOSE! Also returning null is not the same as NullSpace
			return null;
		int i = getY(zombie);
		try {
			return (grid[i][j-1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds in To the left int i = " + i + "  j = " + j);
			System.exit(1);
		}
		return null;
	}
	
	public static GridObject toTheRight(GridObject plant) {
		int j = getX(plant);
		int i = getY(plant);
		//Remember plants cannont be placed in zombie spawn			
		try {
			return (grid[i][j+1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds in To the Right int i = " + i + "  j = " + j);
			System.exit(1);
		}
		return null;
	}
	
	
	public static void move(GridObject gridObject, NullSpace nullSpace){
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
	
	private static int getX(GridObject gridObject) {
		int i,j = 0;
		for (i = 0; i < GRID_HEIGHT; i++) {
			for (j = 0; j < GRID_WIDTH; j++) {
				if (grid[i][j].equals(gridObject))
					return j;
			}
		}
		return 0;
	}
	
	private static int getY(GridObject gridObject) {
		int i,j = 0;
		for (i = 0; i < GRID_HEIGHT; i++) {
			for (j = 0; j < GRID_WIDTH; j++) {
				if (grid[i][j].equals(gridObject))
					return i;
			}
		}
		return 0;
	}
	
	public static boolean isEmpty(int posY, int posX) {
		return (getObject(posX,posY) instanceof NullSpace);
	}
	
	private static GridObject getObject(int i, int j) {
		return grid[i][j];	
	}
	
	private static GridObject castToSubclass(GridObject gridObject) {
		if (gridObject.toShortString().equals("S"))
			return (Sunflower)gridObject;
		else if (gridObject.toShortString().equals("V"))
			return (VenusFlyTrap)gridObject;
	System.out.println("This code in cast to subclass shouldn't be reached");
	return null;
	}
}
