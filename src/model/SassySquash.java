package model;

public class SassySquash extends Plant {
	
	protected static final int FULL_TIME = 5;	
	protected static final int STRENGTH = 250; 
	protected static final int FULL_HEALTH = 500;
	protected static final int PRICE = 10;
	static int currentTime = 0;
	
	public SassySquash() {
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"SassySquash");
	}
	
	/**
	 * This method keeps track of how many game turns have occurred in order
	 * to make the sassysquash available for the player. 
	 */
	public void newTurn() {
		if(currentTime != 0)
				currentTime = currentTime - 1;
	}
	
	/**
	 * This method check for when the sassysquash is available for the player to purchase
	 * and use in the game.
	 * @return True if the current time is equal to zero otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method sets the current time of the sassysquash to zero.
	 */
	public void makeAvailable() { //Not used but might need at some point? 
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
	 * This method sets the sassysquash's current time.
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		SassySquash.currentTime = currentTime;
	}
}
