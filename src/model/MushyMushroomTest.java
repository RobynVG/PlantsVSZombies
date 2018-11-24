package model;

import junit.framework.TestCase;

public class MushyMushroomTest extends TestCase {
	private MushyMushroom m1;
	private Board board;
	
	protected void setUp() {
		m1 = new MushyMushroom();
		Level.level1();
	}
	
	/**
	 * This method tests the newTurn method if current time is not 0.
	 */
	public void testNewTurnNotZero() {
		m1.setCurrentTime(3);
		m1.newTurn();
		assertEquals(m1.getCurrentTime(),2);
	}
	
	/**
	 * This method tests the newTurn() method if current time is 0.
	 */
	public void testNewTurnZero() {
		m1.setCurrentTime(0);
		m1.newTurn();
		assertEquals(m1.getCurrentTime(),0);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is 0.
	 */
	public void testIsAvailableTrue() {
		m1.setCurrentTime(0);
		assertEquals(m1.isAvailable(),true);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is not 0.
	 */
	public void testIsAvailableFalse() {
		m1.setCurrentTime(3);
		assertEquals(m1.isAvailable(),false);
	}
}
