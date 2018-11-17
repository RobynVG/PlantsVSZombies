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
	public Plant(int fullTime, int strength, int health, int price, String objectTitle) {
		this.fullTime = fullTime;
		this.strength = strength;
		this.health = health;
		this.objectTitle = objectTitle;
		this.price = price;
		
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
	}
	
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
	}
	
	/**
	 * This method gets the current time (Will be overridden by the child class).
	 * @return A int, returns the current time.
	 */
	public int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * This method makes the plant available (Will be overridden by the child class).
	 */
	public void makeAvailable() {
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
		return price <= Level.coins;
	}
	
	/**
	 * This method is when a player wants to purchase a plant. 
	 * The player can only purchase the  plant if they have enough coins (Will be overridden by the child class).
	 */
	public void purchase() {
		Level.coins -= price;
	}
	
	/**
	 * This method is for when a plant attacks a zombie (Will be overridden by the child class).
	 * @param zombie (Zombie), a zombie that is being attacked.
	 */
	public void attack(Zombie zombie) {
	}
	
	/**
	 * This method gets the full time.
	 * @return fullTime
	 */
	public int getFullTime() {
		return fullTime;
	}

	/**
	 * This method sets full time.
	 * @param fullTime
	 */
	public void setFullTime(int fullTime) {
		this.fullTime = fullTime;
	}

	/**
	 * This method gets health.
	 * @return health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * This method sets health.
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * This method gets strength.
	 * @return strength.
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * This set strength.
	 * @param strength
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * This method gets the price.
	 * @return price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * This method sets price.
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * This method sets current time.
	 * @param currentTime
	 */
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
}
