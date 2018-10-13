
public class VenusFlyTrap extends Plant {
	static final int fullTime = 3;	//Actually this # - 1. So if 3, its 2 turns.
	static int currentTime = 0; //Time until another Sunflower may be spawned
	protected final int strength = 150; //BEAST
	protected int health = 300;
	protected static int price = 5;
	
	
	public VenusFlyTrap() {
		currentTime = fullTime;
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
	
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
		if (health <= 0)
			GUI.remove(this);
	}
	
	
}
