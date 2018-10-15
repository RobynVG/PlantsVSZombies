
public class VenusFlyTrap extends Plant {
	protected static final int FULL_TIME = 3;	//Actually this # - 1. So if 3, its 2 turns.
	protected static final int STRENGTH = 150; //BEAST
	protected static final int FULL_HEALTH = 320;
	protected static final int PRICE = 5;
	
	static int currentTime = 0;
	
	
	public VenusFlyTrap() {
		fullTime = FULL_TIME;
		currentTime = fullTime; //new object created - reset static timer
		strength = STRENGTH;
		health = FULL_HEALTH;
		price = PRICE;
	}

	//VenusFlyTraps only zombies in front of them
	public void attack(Zombie zombie) {
		zombie.loseHealth(strength);
	}
	
	public String toString() {
		return "[ V ]";
	}
	
	//Can't inherit the function since this one deals with static vars. Can override though
	public void newTurn() {
		if (currentTime!= 0)
			currentTime = currentTime - 1;
	}
	
	public boolean isAvailable() {
		return (currentTime == 0);
	}
	
	public boolean isAffordable() {
		return (price <= Level.coins);
	}
	
	public void makeAvailable() {
		currentTime = 0;
	}
	
	public void purchase() { //Static var (price) must redefine function here
		Level.coins -= price;
	}
	
//	public void loseHealth(int zombieStrength) {
//		health = health - zombieStrength;
//		if (health <= 0)
//			GUI.remove(this);
//	}
	
	
}
