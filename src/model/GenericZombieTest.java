package model;

import junit.framework.TestCase;

public class GenericZombieTest extends TestCase {
	
	private GenericZombie genericZombie = null;
	private SunFlower sunFlower = null;
	private VenusFlyTrap v1;
	private VenusFlyTrap v2;
	
	/**
	 * This method constructs the object to be tested.
	 */
	protected void setUp() {
		Board.setupGrid();
		genericZombie = new GenericZombie();
		sunFlower = new SunFlower();
		v1 = new VenusFlyTrap();
		v2 = new VenusFlyTrap();
	}
	
	/**
	 * This method test loseHealth() method.
	 */
	public void testLoseHealth() {
		int health = genericZombie.getHealth();
		genericZombie.loseHealth(20);	
		assertEquals(genericZombie.getHealth(), health - 20);
		//assertEquals("Error in loseHealth Method",250);
	}
	
	/**
	 * This method test if the zombie dies.
	 */
	public void testLoseHealthDie() {
		Board.placeZombie(genericZombie, 2, 2);
		genericZombie.loseHealth(1000);	
		boolean boardContainsZombie = true;
		if (!Board.zombiesOnBoard.contains(genericZombie))
			boardContainsZombie = false;
		assertEquals(boardContainsZombie, false);
		//assertEquals("Error in loseHealth Method",250);
	}
	
	/**
	 * This method test the attack() method.
	 */
	public void testAttack() {
		int plantHealth = v1.getHealth();
		genericZombie.attack(v1);
		assertEquals(v1.getHealth(), plantHealth - genericZombie.getStrength());
	}
	
	/**
	 * This method test the if the zombie moves on the grid.
	 */
	public void testGoAdvance() {
		Board.placeZombie(genericZombie, 2, 2);
		genericZombie.go();
		assertEquals(Board.getObject(2,1), genericZombie);
	}
	
	/**
	 * This method test the if attack work with the go method.
	 */
	public void testGoAttack() {
		Board.placeZombie(genericZombie, 3, 3);
		Board.placePlant(v2,2,3);
		genericZombie.go();
		assertEquals(v2.getHealth(), VenusFlyTrap.FULL_HEALTH - GenericZombie.STRENGTH);
	}
	
//	public void go() {
//		GridObject left = Board.toTheLeft(this);
//		if (left instanceof Plant )
//			attack((Plant)left);
//		else if (left instanceof NullSpace)
//			advance((NullSpace)left);
//		//else if (left == null) //off the board plants lose!
//			
//		//else
//			//another zombie;  //Should theoretically only happen if the zombie before this one is attacking a plant
//	}
}
