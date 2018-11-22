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
	 * This method reduces the amount of money the player has when purchasing a sassysquash.
	 */
	public void purchase() {
		Level.coins -= price;
	}
	
	/**
	 * This method checks if the player has the appropriate amount of coins to purchase
	 * the sassysquash plant.
	 * @return True, if the player has enough coins otherwise false.
	 */
	public boolean isAffordable() {
		return (price <= Level.coins);
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
		Unicorn.currentTime = currentTime;
	}
	
	/**
	 * This method reduces the sassysquash's health. 
	 * If the sassysquash's is equal or less then zero, the sassysquash is removed from the board.
	 * @param A int which is the zombie's strength.
	 */
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
		if (health <= 0)
			Board.remove(this);
	}
	
	
}
