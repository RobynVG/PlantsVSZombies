package model;

public class PeaShooter extends Plant {

	protected static final int FULL_TIME = 2;
	protected static final int STRENGTH = 150;
	protected static final int FULL_HEALTH = 500;
	protected static final int PRICE = 100;
	static int currentTime = 0;

	/**
	 * This constructor, constructs a pea shooter by calling the plant class with a
	 * super method.
	 */
	public PeaShooter() {
		super(FULL_TIME, STRENGTH, FULL_HEALTH, PRICE, "PeaShooter");
	}

	/**
	 * This method keeps track of how many game turns have occurred in order to make
	 * the pea shooter available for the player.
	 */
	public void newTurn() {
		if (currentTime != 0)
			currentTime = currentTime - 1;
	}

	/**
	 * This method check for when the pea shooter is available for the player to
	 * purchase and use in the game.
	 * 
	 * @return True if the current time is equal to zero otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}

	/**
	 * This method sets the current time of the pea shooter to zero.
	 */
	public void makeAvailable() {
		currentTime = 0;
	}

	/**
	 * This method gets the current time.
	 * 
	 * @return A int which is the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}

	/**
	 * This method sets the pea shooter's current time.
	 * 
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		PeaShooter.currentTime = currentTime;
	}

	/**
	 * This method attacks the Zombie.
	 */
	public void attack(Zombie zombie) {
		zombie.loseHealth(strength);
	}

	/**
	 * This method is when the Pea Shooter shoots a zombie.
	 */
	public void go(Board board) {
		for (int i = board.getX(this); i < board.GRID_WIDTH; i++) {
			if (board.getObject(board.getY(this), i) instanceof Zombie) {
				attack((Zombie) board.getObject(board.getY(this), i));
			}
		}
	}
}
