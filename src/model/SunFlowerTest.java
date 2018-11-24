package model;

import junit.framework.TestCase;

public class SunFlowerTest extends TestCase {
	
	private SunFlower s1;
	private Board board;
	
	protected void setUp() {
		s1 = new SunFlower();
		Level.level1();
	}
	
	/**
	 * This method tests the newTurn method if current time is not 0.
	 */
	public void testNewTurnNotZero() {
		s1.setCurrentTime(3);
		s1.newTurn();
		assertEquals(s1.getCurrentTime(),2);
	}
	
	/**
	 * This method tests the newTurn() method if current time is 0.
	 */
	public void testNewTurnZero() {
		s1.setCurrentTime(0);
		s1.newTurn();
		assertEquals(s1.getCurrentTime(),0);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is 0.
	 */
	public void testIsAvailableTrue() {
		s1.setCurrentTime(0);
		assertEquals(s1.isAvailable(),true);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is not 0.
	 */
	public void testIsAvailableFalse() {
		s1.setCurrentTime(3);
		assertEquals(s1.isAvailable(),false);
	}
}
