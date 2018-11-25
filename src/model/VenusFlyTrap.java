package model;

public class VenusFlyTrap extends Plant {
	protected static final int FULL_TIME = 3;	
	protected static final int STRENGTH = 150; 
	protected static final int FULL_HEALTH = 320;
	protected static final int PRICE = 150;
	static int currentTime = 0;
	
	/**
	 * This constructor, constructs a VenusFlyTrap by calling the plant class with a super method.
	 */
	public VenusFlyTrap() {
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"VenusFlyTrap");
	}

	
	/**
	 *  This method only attacks zombies in front of them.  
	 */
	public void attack(Zombie zombie) {
		zombie.loseHealth(strength);
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
	 * This method gets the current time.
	 * @return A int, returns the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method sets the current time for the VenusFlyTrap class.
	 * This methods sets the current time.
	 */
	public void setCurrentTime(int currentTime) {
		VenusFlyTrap.currentTime = currentTime;
	}
}