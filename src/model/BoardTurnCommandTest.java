package model;

public class BoardTurnCommandTest {
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
	
	
}
