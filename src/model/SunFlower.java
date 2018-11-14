package model;

public class SunFlower extends Plant {

	protected static final int FULL_TIME = 0;	//Actually this # - 1. So if 3, its 2 turns.
	protected static final int STRENGTH = 0;
	protected static final int FULL_HEALTH = 100;
	protected static final int PRICE = 2;
	public static final int COIN_BONUS = 1;
	static int currentTime = 0;
	
	/**
	 * This constructs a sunflower.
	 */
	public SunFlower() {
		fullTime = FULL_TIME;
		currentTime = fullTime; //new object created - reset static timer
		strength = STRENGTH;
		health = FULL_HEALTH;
		price = PRICE;
		objectTitle = "SunFlower";
	}
	
	/**
	 * This method is when a player wants to purchase a sunflower. 
	 * The player can only purchase the sunflower if they have enough coins.
	 */
	public void purchase() { //Static var (price) must redefine function here
		Level.coins -= PRICE;
	}
	
	/**
	 * This method prints out a sunflower string on the grid. 
	 * @return A String, this method returns a sunflower string equivalent.
	 */
	public String toString() {
		return "[ S ]";
	}
	
	//Can't inherit the function since this one deals with static vars. Can override though
	/**
	 * This method keeps track of the turns for when the sunflower is available again.
	 */
	public void newTurn() {
		if (currentTime!= 0)
			currentTime = currentTime - 1;
	}
	
	/**
	 * This method checks if the sunflower is available.
	 * @return A boolean, true if it is available otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method checks if the player can afford the sunflower.
	 * @return A boolean, true if it is affordable otherwise false.
	 */
	public boolean isAffordable() {
		return (price <= Level.coins);
	}
	
	/**
	 * This method makes the sunflower available to the player.
	 */
	public void makeAvailable() { //Not used but might need at some point? 
		currentTime = 0;
	}
	
	/**
	 * This method reduces the sunflower's health when a zombie has attack the sunflower.
	 * If the sunflower's health is zero it is removed from the board.
	 * @param zombieStrength (int), the zombie's strength for attacking a sunflower.
	 */
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
		if (health <= 0)
			Board.remove(this);
	}
}
