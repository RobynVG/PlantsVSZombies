package model;

public abstract class Plant extends GridObject{
	public int fullTime; 
	public int currentTime; //available again at 0, when used counts down from full time;
	protected int health;
	protected int strength;
	protected int price;

	/**
	 * This constructs a plant.
	 */
	public Plant() {
		purchase();
		resetTime(); //Reset for particular plant when they are born (Wait until you can plant another)
	}
	
	/**
	 * This method allows the plants to move on the board.
	 */
	public void go() {
		GridObject right = Board.toTheRight(this);
		if (right instanceof Zombie)
			attack((Zombie)right);
	}
	
	/**
	 * This method keeps track of the game turn.
	 */
	public void newTurn() { //on every turn should only be called ONCE per plant type.
		if (currentTime != 0)
			currentTime -- ;		
	}
	
	//tough lesson on inheritance here :( if loose Health is called on a Plant (not subclass)
	//object, it will execute here not in the subclass. Objects are taken out of the grid as
	//grid objects then cast to Plant or Zombie. Here it is a problem...
	/**
	 * This method reduces the plant's health when a zombie has attack the plant.
	 * If the plant's health is zero it is removed from the board.
	 * @param zombieStrength (int), the zombie's strength for attacking a plant.
	 */
	public void loseHealth(int zombieStrength) {
		health = health - zombieStrength;
		if (health <= 0)
			Board.remove(this);
	}
	
	/**
	 * This method resets the time (Will be overridden by the child class).
	 */
	public void resetTime() { //when a plant is placed
		currentTime = fullTime;
	}
	
	/**
	 * This method gets the current time (Will be overridden by the child class).
	 * @return A int, returns the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method returns an empty string (Will be overridden by the child class).
	 */
	public String toString() {
		return "";
	}
	
	/**
	 * This method makes the plant available (Will be overridden by the child class).
	 */
	public void makeAvailable() {
		currentTime = 0;
	}
	
	/**
	 * This method checks if the plant is available for the round (Will be overridden by the child class).
	 * @return A boolean, true if it is available otherwise false.
	 */
	public boolean isAvailable() {
		System.out.println("This method should have been overridden by my children");
		return false;
	}
	
	/**
	 * This method checks if the player can afford the plant (Will be overridden by the child class).
	 * @return A boolean, true if it is affordable otherwise false.
	 */
	public boolean isAffordable() {
		System.out.println("This method should have been overridden by my children");
		return false;
	}
	
	/**
	 * This method is when a player wants to purchase a plant. 
	 * The player can only purchase the  plant if they have enough coins (Will be overridden by the child class).
	 */
	public void purchase() {
		System.out.println("This method should have been overridden by my children");
	}
	
	/**
	 * This method is for when a plant attacks a zombie (Will be overridden by the child class).
	 * @param zombie (Zombie), a zombie that is being attacked.
	 */
	public void attack(Zombie zombie) {
	}
}
