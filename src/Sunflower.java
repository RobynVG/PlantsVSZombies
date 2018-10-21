
public class Sunflower extends Plant {
	protected static final int FULL_TIME = 2;	//Actually this # - 1. So if 3, its 2 turns.
	protected static final int STRENGTH = 0;
	protected static final int FULL_HEALTH = 100;
	protected static final int PRICE = 2;
	public static final int COIN_BONUS = 1;
	
	static int currentTime = 0;
	
	public Sunflower() {
		currentTime = fullTime;//Should reset in parent. This constructor probably not needed
	}
	
	public void purchase() { //Static var (price) must redefine function here
		Level.coins -= price;
	}

	public String toString() {
		return "[ S ]";
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
	
	public void makeAvailable() { //Not used but might need at some point?
		currentTime = 0;
	}
	
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
		if (health <= 0)
			GUI.remove(this);
	}
}
