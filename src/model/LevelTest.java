package model;

import java.util.ArrayList;

import junit.framework.TestCase;

public class LevelTest extends TestCase {

	protected void setUp() throws Exception {
		Level.level1();
	}

	/**
	 * This method test if level empty is false.
	 */
	public void testLevelIsEmptyFalse() {
		assertEquals(Level.isEmpty(),false);
	}
	
	/**
	 * This method test if level empty is true.
	 */
	public void testLevelIsEmptyTrue() {
		Level.allZombies = new ArrayList<Zombie>();
		assertEquals(Level.isEmpty(),true);
	}
	
	/**
	 * This method test plant affordable is true.
	 */
	public void testPlantAffordableTrue() {
		Level.coins = 9999;
		assertEquals(Level.plantAffordable(),true);
	}
	
	/**
	 * This method test plant affordable is false.
	 */
	public void testPlantAffordableFalse() {
		Level.coins = -1;
		assertEquals(Level.plantAffordable(),false);
	}
}
