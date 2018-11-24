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
		super(FULL_TIME,STRENGTH,FULL_HEALTH,PRICE,"VenusFlyTrap");
	}

	//VenusFlyTraps only zombies in front of them
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
	 * This method gets the current time (Will be overridden by the child class).
	 * @return A int, returns the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method sets the current time for the VenusFlyTrap class.
	 */
	public void setCurrentTime(int currentTime) {
		VenusFlyTrap.currentTime = currentTime;
	}
}