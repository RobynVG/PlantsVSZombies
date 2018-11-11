package model;

public class VenusFlyTrap extends Plant {
	protected static final int FULL_TIME = 3;	//Actually this # - 1. So if 3, its 2 turns.
	protected static final int STRENGTH = 150; //BEAST
	protected static final int FULL_HEALTH = 320;
	protected static final int PRICE = 5;
	static int currentTime = 0;
	
	/**
	 * This construct a venus flytrap.
	 */
	public VenusFlyTrap() {
		fullTime = FULL_TIME;
		currentTime = fullTime; //new object created - reset static timer
		strength = STRENGTH;
		health = FULL_HEALTH;
		price = PRICE;
		imageTitle = "VenusFlyTrap.png";
	}

	//VenusFlyTraps only zombies in front of them
	/**
	 *  This method only attacks zombies in front of them.  
	 */
	public void attack(Zombie zombie) {
		zombie.loseHealth(strength);
	}
	
	/**
	 * This method prints out a venus flytrap string on the grid. 
	 * @return A String, this method returns a venus flytrap string equivalent.
	 */
	public String toString() {
		return "[ V ]";
	}
	
	//Can't inherit the function since this one deals with static vars. Can override though
	/**
	 * This method keeps track of the turns for when the venus flytrap is available again.
	 */
	public void newTurn() {
		if (currentTime!= 0)
			currentTime = currentTime - 1;
	}
	
	/**
	 * This method checks if the venus flytrap is available.
	 * @return A boolean, true if it is available otherwise false.
	 */
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	/**
	 * This method checks if the player can afford the venus flytrap.
	 * @return A boolean, true if it is affordable otherwise false.
	 */
	public boolean isAffordable() {
		return (price <= Level.coins);
	}
	
	/**
	 * This method makes the venus flytrap available to the player.
	 */
	public void makeAvailable() {
		currentTime = 0;
	}
	
	/**
	 * This method is when a player wants to purchase a venus flytrap. 
	 * The player can only purchase the venus flytrap if they have enough coins.
	 */
	public void purchase() { //Static var (price) must redefine function here
		Level.coins -= PRICE;
	}
	
//	public void loseHealth(int zombieStrength) {
//		health = health - zombieStrength;
//		if (health <= 0)
//			GUI.remove(this);
//	}
	
	
}