package model;

import junit.framework.TestCase;

public class SassySquashTest extends TestCase {
	private SassySquash ss1;
	private Board board;
	
	protected void setUp() {
		ss1 = new SassySquash();
		Level.level1();
	}
	
	public void testNewTurnNotZero() {
		ss1.setCurrentTime(3);
		ss1.newTurn();
		assertEquals(ss1.getCurrentTime(),2);
	}
	
	public void testNewTurnZero() {
		ss1.setCurrentTime(0);
		ss1.newTurn();
		assertEquals(ss1.getCurrentTime(),0);
	}
	
	public void testIsAvailableTrue() {
		ss1.setCurrentTime(0);
		assertEquals(ss1.isAvailable(),true);
	}
	
	public void testIsAvailableFalse() {
		ss1.setCurrentTime(3);
		assertEquals(ss1.isAvailable(),false);
	}
}
