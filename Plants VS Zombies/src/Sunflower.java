
public class Sunflower extends Plant {
	static int fullTime = 2;	//Actually this number -1
	static int currentTime = 0; //Time until another Sunflower may be spawned
	protected int health = 100; //1 Hit, weak flower
	protected int strength = 0; //Can't hurt anyone :(
	
	public Sunflower() {
		currentTime = fullTime;//Should reset in parent. This constructor probably not needed
	}

	public String toString() {
		return "[ S ]";
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
