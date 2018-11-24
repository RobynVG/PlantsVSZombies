package model;

import junit.framework.TestCase;

public class UnicornTest extends TestCase {
	private Unicorn u1;
	private Board board;

	protected void setUp() {
		u1 = new Unicorn();
		Level.level1();
	}
	
	public void testNewTurnNotZero() {
		u1.setCurrentTime(3);
		u1.newTurn();
		assertEquals(u1.getCurrentTime(),2);
	}
	
	public void testNewTurnZero() {
		u1.setCurrentTime(0);
		u1.newTurn();
		assertEquals(u1.getCurrentTime(),0);
	}
	
	public void testIsAvailableTrue() {
		u1.setCurrentTime(0);
		assertEquals(u1.isAvailable(),true);
	}
	
	public void testIsAvailableFalse() {
		u1.setCurrentTime(3);
		assertEquals(u1.isAvailable(),false);
	}
}
