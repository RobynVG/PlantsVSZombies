package model;

import junit.framework.TestCase;

public class SunFlowerTest extends TestCase {
	
	private SunFlower s1;
	private Board board;
	
	protected void setUp() {
		s1 = new SunFlower();
		Level.level1();
	}
	
	public void testNewTurnNotZero() {
		s1.setCurrentTime(3);
		s1.newTurn();
		assertEquals(s1.getCurrentTime(),2);
	}
	
	public void testNewTurnZero() {
		s1.setCurrentTime(0);
		s1.newTurn();
		assertEquals(s1.getCurrentTime(),0);
	}
	
	public void testIsAvailableTrue() {
		s1.setCurrentTime(0);
		assertEquals(s1.isAvailable(),true);
	}
	
	public void testIsAvailableFalse() {
		s1.setCurrentTime(3);
		assertEquals(s1.isAvailable(),false);
	}
	
}
