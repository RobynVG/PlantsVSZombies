package model;

import java.util.ArrayList;

import junit.framework.TestCase;

public class LevelTest extends TestCase {

	protected void setUp() throws Exception {
		Level.level1();
	}

	public void testLevelIsEmptyFalse() {
		assertEquals(Level.isEmpty(),false);
	}
	
	public void testLevelIsEmptyTrue() {
		Level.allZombies = new ArrayList<Zombie>();
		assertEquals(Level.isEmpty(),true);
	}
	
	public void testPlantAffordableTrue() {
		Level.coins = 9999;
		assertEquals(Level.plantAffordable(),true);
	}
	
	public void testPlantAffordableFalse() {
		Level.coins = 9999;
		assertEquals(Level.plantAffordable(),true);
	}
}
