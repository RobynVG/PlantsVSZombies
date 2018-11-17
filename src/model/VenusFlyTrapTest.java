package model;

import junit.framework.TestCase;

public class VenusFlyTrapTest extends TestCase {
	private GenericZombie g1;
	private SunFlower s1;
	private VenusFlyTrap v1;
	private VenusFlyTrap v2;
	
	protected void setUp() {
		Board.setupGrid();
		g1 = new GenericZombie();
		s1 = new SunFlower();
		v1 = new VenusFlyTrap();
		v2 = new VenusFlyTrap();
	}
	
	/**
	 * This method test the attack() method.
	 */
	public void testAttack() {
		int zombieHealth = g1.getHealth();
		v1.attack(g1);
		assertEquals(g1.getHealth(), zombieHealth - v1.getStrength());
	}
	
	/**
	 * This method test the if attack work with the go method .
	 */
	public void testGoAttack() {
		Board.placePlant(v1, 2,3);
		Board.placeZombie(g1,3,3);
		v1.go();
		assertEquals(g1.getHealth(), GenericZombie.FULL_HEALTH - VenusFlyTrap.STRENGTH);
	}
}
