
public abstract class Plant extends GridObject{
	public int fullTime; 
	public int currentTime; //available again at 0, when used counts down from full time;
	protected int health;
	protected int strength;
	protected int price;

	public Plant() {
		purchase();
		resetTime(); //Reset for particular plant when they are born (Wait until you can plant another)
	}
	
	public void go() {
		GridObject right = GUI.toTheRight(this);
		if (right instanceof Zombie)
			attack((Zombie)right);
	}
	
	public void newTurn() { //on every turn should only be called ONCE per plant type.
		if (currentTime != 0)
			currentTime -- ;		
	}
	
	//tough lesson on inheritance here :( if loose Health is called on a Plant (not subclass)
	//object, it will execute here not in the subclass. Objects are taken out of the grid as
	//grid objects then cast to Plant or Zombie. Here it is a problem...
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
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
		System.out.println("This method should have been overridden by my children");
		return false;
	}
	
	public boolean isAffordable() {
		System.out.println("This method should have been overridden by my children");
		return false;
	}
	
	public void purchase() {
		System.out.println("This method should have been overridden by my children");
	}
	
	public void attack(Zombie zombie) {
	}
}
