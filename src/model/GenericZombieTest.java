package model;

import junit.framework.TestCase;

public class GenericZombieTest extends TestCase {
	
	private GenericZombie genericZombie = null;
	private SunFlower sunFlower = null;
	
	/**
	 * This method constructs the object to be tested.
	 */
	protected void setUp() {
		genericZombie = new GenericZombie();
		sunFlower = new SunFlower();
	}
	
	/**
	 * This method test lose health in Generic Zombie.
	 */
	public void testLoseHealth() {
		genericZombie.loseHealth(0);
		assertEquals("Error in loseHealth Method",250);
	}
}
