package model;

public class Walnut extends Plant {
	protected static final int FULL_TIME = 6;	
	protected static final int STRENGTH = 0; 
	protected static final int FULL_HEALTH = 2000;
	protected static final int PRICE = 50;
	static int currentTime = 0;
	
	/**
	 * The constructor, constructs a Walnut by calling the plant class with a super method.
	 */
	public Walnut() {
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"Walnut");
	}
	
	/**
	 * This method keeps track of how many game turns have occurred in order
	 * to make the walnut available for the player. 
	 */
	public void newTurn() {
		if(currentTime != 0)
				currentTime = currentTime - 1;
	}
	
	/**
	 * This method check for when the walnut is available for the player to purchase
	 * and use in the game.
	 * @return True if the current time is equal to zero otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method sets the current time of the walnut plant to zero.
	 */
	public void makeAvailable() { 
		currentTime = 0;
	}
	
	/**
	 * This method gets the current time.
	 * @return A int which is the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method sets the walnut's current time.
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		Walnut.currentTime = currentTime;
	}
}
