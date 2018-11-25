package model;

import junit.framework.TestCase;

public class VenusFlyTrapTest extends TestCase {
	private GenericZombie g1;
	private SunFlower s1;
	private VenusFlyTrap v1,v2;
	private Board board;
	
	protected void setUp() {
		board = new Board();
		board.setupGrid();
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
	 * This method test the if attack works with the go method .
	 */
	public void testGoAttack() {
		board.placePlant(v1, 3,2);
		board.placeZombie(g1,3,3);
		v1.go(board);
		assertEquals(g1.getHealth(), GenericZombie.FULL_HEALTH - VenusFlyTrap.STRENGTH);
	}
	
	/**
	 * This method tests the newTurn method if current time is not 0.
	 */
	public void testNewTurnNotZero() {
		v1.setCurrentTime(3);
		v1.newTurn();
		assertEquals(v1.getCurrentTime(),2);
	}
	
	/**
	 * This method tests the newTurn() method if current time is 0.
	 */
	public void testNewTurnZero() {
		v1.setCurrentTime(0);
		v1.newTurn();
		assertEquals(v1.getCurrentTime(),0);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is 0.
	 */
	public void testIsAvailableTrue() {
		v1.setCurrentTime(0);
		assertEquals(v1.isAvailable(),true);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is not 0.
	 */
	public void testIsAvailableFalse() {
		v1.setCurrentTime(3);
		assertEquals(v1.isAvailable(),false);
	}
	
	/**
	 * This method tests the isAffordable method if the price < coins.
	 */
	public void testIsAffordableTrue() {
		Level.coins = 1000;
		assertEquals(v1.isAffordable(),true);
	}
	
	/**
	 * This method tests the isAffordable method if the price = coins.
	 */
	public void testIsAffordableFalse() {
		Level.coins = 5;
		assertEquals(v1.isAffordable(),false);
	}
	
	/**
	 * This method tests the purchase method.
	 */
	public void testPurchase() {
		Level.coins = 6;
		v1.purchase();
		assertEquals(1, Level.coins);
	}
	
	/**
	 * This method tests the loseHealth method.
	 */
	public void testLoseHealth() {
		v1.setHealth(50);
		v1.loseHealth(5);
		assertEquals(45,v1.getHealth());
	}
}
