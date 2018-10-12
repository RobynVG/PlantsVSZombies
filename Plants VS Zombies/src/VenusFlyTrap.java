
public class VenusFlyTrap extends Plant {
	static int fullTime = 3;	//Actually this # - 1
	static int currentTime = 0; //Time until another Sunflower may be spawned
	protected int health = 300;
	protected int strength = 150; //BEAST
	
	public VenusFlyTrap() {
		currentTime = fullTime;
	}

	public String toString() {
		return "[ V ]";
	}
	
	//Can't inherit the function since this one deals with static vars. Can override though
	public void newTurn() {
		if (currentTime!= 0)
			currentTime = currentTime - 1;
	}
	
	public boolean isAvailable() { //Would be nice to put in Plant but apparently static inheritance doesn't work so well
		return (currentTime == 0);
	}
	
	public void makeAvailable() {
		currentTime = 0;
	}
}
