package model;

import junit.framework.TestCase;

public class BaNanaTest extends TestCase{

	private BaNana b1;
	private Board board;
	
	
	protected void setUp() {
		b1 = new BaNana();
		Level.level1();
	}
	
	/**
	 * This method tests the newTurn method if current time is not 0.
	 */
	public void testNewTurnNotZero() {
		b1.setCurrentTime(3);
		b1.newTurn();
		assertEquals(b1.getCurrentTime(),2);
	}
	
	/**
	 * This method tests the newTurn() method if current time is 0.
	 */
	public void testNewTurnZero() {
		b1.setCurrentTime(0);
		b1.newTurn();
		assertEquals(b1.getCurrentTime(),0);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is 0.
	 */
	public void testIsAvailableTrue() {
		b1.setCurrentTime(0);
		assertEquals(b1.isAvailable(),true);
	}
	
	/**
	 * This method tests the isAvailable() method if current time is not 0.
	 */
	public void testIsAvailableFalse() {
		b1.setCurrentTime(3);
		assertEquals(b1.isAvailable(),false);
	}
}
