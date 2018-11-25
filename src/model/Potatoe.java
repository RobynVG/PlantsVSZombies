package model;

public class Potatoe extends Plant {

	protected static final int FULL_TIME = 2;	
	protected static final int STRENGTH = 1000; 
	protected static final int FULL_HEALTH = 1;
	protected static final int PRICE = 25;
	static int currentTime = 0;
	
	/**
	 * This constructor, constructs a Potatoe by calling the plant class with a super method.
	 */
	public Potatoe() {
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"Potatoe");
	}
	
	/**
	 * This method reduces the amount of money the player has when purchasing a Potatoe.
	 */
	public void purchase() {
		Level.coins -= price;
	}
	
	/**
	 * This method checks if the player has the appropriate amount of coins to purchase
	 * the Potatoe.
	 * @return True, if the player has enough coins otherwise false.
	 */
	public boolean isAffordable() {
		return (price <= Level.coins);
	}
	
	/**
	 * This method keeps track of how many game turns have occurred in order
	 * to make the Potatoe available for the player. 
	 */
	public void newTurn() {
		if(currentTime != 0)
				currentTime = currentTime - 1;
	}
	
	/**
	 * This method check for when the Potatoe is available for the player to purchase
	 * and use in the game.
	 * @return True if the current time is equal to zero otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method sets the current time of the Potatoe to zero.
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
	 * This method sets the Potatoe's current time.
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		Potatoe.currentTime = currentTime;
	}

}
