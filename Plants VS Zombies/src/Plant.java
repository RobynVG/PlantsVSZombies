
public abstract class Plant extends GridObject{
	public int fullTime; 
	public int currentTime; //available again at 0, when used counts down from full time;
	protected int health;
	protected int strength;

	public Plant() {
		resetTime(); //Reset for particular plant when they are born (Wait until you can plant another)
	}
	
	public void newTurn() { //on every turn
		if (currentTime != 0)
			currentTime -- ;		
	}
	
	public void loseHealth(int strength) {
		health -= strength;
		if (health <= 0)
			GUI.remove(this);
	}
	
	public void resetTime() { //when a plant is placed
		currentTime = fullTime;
	}
	
	public int getCurrentTime() {
		return currentTime;
	}
	
	public String toString() {
		return "";
	}
	
	public void makeAvailable() {
		currentTime = 0;
	}
	
	public boolean isAvailable() {
		return true;
	}
	
	

}
