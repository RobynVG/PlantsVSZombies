package model;

import junit.framework.TestCase;

public class BaNanaTest extends TestCase{

	private BaNana b1;
	private Board board;
	
	protected void setUp() {
		b1 = new BaNana();
		Level.level1();
	}
	
	public void testNewTurnNotZero() {
		b1.setCurrentTime(3);
		b1.newTurn();
		assertEquals(b1.getCurrentTime(),2);
	}
	
	public void testNewTurnZero() {
		b1.setCurrentTime(0);
		b1.newTurn();
		assertEquals(b1.getCurrentTime(),0);
	}
	
	public void testIsAvailableTrue() {
		b1.setCurrentTime(0);
		assertEquals(b1.isAvailable(),true);
	}
	
	public void testIsAvailableFalse() {
		b1.setCurrentTime(3);
		assertEquals(b1.isAvailable(),false);
	}
}
