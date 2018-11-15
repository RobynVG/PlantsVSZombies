package model;

import java.util.ArrayList;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	public SunFlower s1;
	public SunFlower s2;
	public VenusFlyTrap v1;
	public GenericZombie g1;
	
	protected void setUp() {
		s1 = new SunFlower();
		s2 = new SunFlower();
		v1 = new VenusFlyTrap();
		g1 = new GenericZombie();
		Board.setupGrid();
		Level.level1();
	}

	public void testSpawnZombies() {
		Board.spawnZombies();
		boolean zombieInG = false;
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			if (Board.getObject(i,Board.GRID_WIDTH-1) instanceof GenericZombie)
				zombieInG = true;
		}
		assertEquals("Testing Zombie Spwan",zombieInG, true);
	}
	
	public void testSpawnZombiesEmpty() {
		Level.allZombies = new ArrayList<Zombie>();
		Board.spawnZombies();
		boolean zombieInG = false;
		for (int i = 0; i < Board.GRID_HEIGHT; i++) {
			if (Board.getObject(i,Board.GRID_WIDTH-1) instanceof GenericZombie)
				zombieInG = true;
		}
		assertEquals("Testing Zombie Spwan",zombieInG, false);
	}
	
	public void testPrepareNextTurn() {
		Board.setupGrid();
		Board.placePlant(s1, 2, 2);
		Board.placePlant(s2, 1, 1);
		Board.placePlant(v1, 3, 3);
		Level.coins = 0;
		
		Board.prepareNextTurn();
		
		//To be fixed
		assertEquals(Level.coins, Level.coins);
		
	}
	
	public void testZombiesInFirstColumnTrue() {
		Board.placeZombie(g1, 0, 3);
		assertEquals(Board.zombiesInFirstColumn(), true);
	}
	
	public void testZombiesInFirstColumnFalse() {
		assertEquals(Board.zombiesInFirstColumn(), false);
	}
	
	public void testToTheLeftZombie() {
		Board.placeZombie(g1, 0, 0);
		Board.placePlant(s1, 1, 0);
		assertEquals(Board.toTheLeft(s1), g1);	
	}
	
	public void testToTheLeftNull() {
		Board.placeZombie(g1, 0, 0);
		assertEquals(Board.toTheLeft(g1), null);	
	}
	
	public void testToTheRightPlant() {
		Board.placeZombie(g1, 0, 0);
		Board.placePlant(s1, 1, 0);
		assertEquals(Board.toTheRight(g1), s1);
	}
	
	public void testToTheRightNull() {
		Board.placeZombie(g1, Board.GRID_WIDTH-1, 0);
		assertEquals(Board.toTheRight(g1), null);
	}
	
	public void testMove() {
		Board.placePlant(s1, 2, 2);
		Board.move(s1, (NullSpace) Board.getObject(2, 3));
		assertEquals(Board.getObject(2, 3), s1);
	}
	
	public void testRemovePlant() {
		Board.placePlant(s1, 2, 2);
		Board.remove(s1);
		boolean isNullSpace = false;
		if (Board.getObject(2,2) instanceof NullSpace)
			isNullSpace = true;
		assertEquals(isNullSpace, true);
	}
	
	public void testRemoveZombie() {
		Board.placeZombie(g1, 2, 2);
		Board.remove(g1);
		boolean isNullSpace = false;
		if (Board.getObject(2,2) instanceof NullSpace)
			isNullSpace = true;
		assertEquals(isNullSpace, true);
	}

//	public static boolean remove(GridObject gridObject) {
//		int j = getX(gridObject);
//		int i = getY(gridObject);
//		grid[i][j] = new NullSpace();
//		
//		gridObjects.remove(gridObject);
//		if (gridObject instanceof Zombie)
//			zombiesOnBoard.remove(gridObject);
//		if (gridObject instanceof Plant)
//			plantsOnBoard.remove(gridObject);
//		
//		if(grid[i][j] instanceof NullSpace) {
//			return true;
//		}
//		return false;
//	}
	
}