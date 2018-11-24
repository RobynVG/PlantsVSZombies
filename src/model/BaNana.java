package model;

public class BaNana extends Plant {
	protected static final int FULL_TIME = 6;	
	protected static final int STRENGTH = 250; 
	protected static final int FULL_HEALTH = 500;
	protected static final int PRICE = 10;
	static int currentTime = 0;
	
	public BaNana() {
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"BaNana");
	}
	
	/**
	 * This method keeps track of how many game turns have occurred in order
	 * to make the baNana available for the player. 
	 */
	public void newTurn() {
		if(currentTime != 0)
				currentTime = currentTime - 1;
	}
	
	/**
	 * This method check for when the baNana is available for the player to purchase
	 * and use in the game.
	 * @return True if the current time is equal to zero otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method gets the current time.
	 * @return A int which is the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method sets the baNana's current time.
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		BaNana.currentTime = currentTime;
	}
}
