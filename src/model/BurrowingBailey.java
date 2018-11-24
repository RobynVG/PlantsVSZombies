package model;

public class BurrowingBailey extends Zombie{
	public static final int STRENGTH = 100;
	public static final int FULL_HEALTH = 200;
	
	public BurrowingBailey() {
		super(STRENGTH, FULL_HEALTH, "BurrowingBailey");
	}
	
	/**
	 * This method allows the zombies to move on the board.
	 */
	public void go(Board board) {
		GridObject l = board.toTheLeft(this);
		GridObject ll = board.toTheLeft(l);
		
		//BurrowingBailey burrows under the next plant if there is a NullSPace
		//To its left
		if (l instanceof Plant && ll instanceof NullSpace)
			board.move(this, (NullSpace) ll);
		//Regular move to the left.
		else if (l instanceof NullSpace)
			board.move(this, (NullSpace) l);
		//Regular attack
		else if (l instanceof Plant)
			attack((Plant)l);
	}
	
}