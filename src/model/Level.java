package model;
import java.util.ArrayList;

public class Level {
	public static ArrayList<Plant> allPlants; //Not actually all plants, its all plant TYPES (1 instance of each)
	private static ArrayList<Zombie> allZombies; //Actually all zombies
	public static int coins = 10; 
	
	
	public static boolean isEmpty() {
		return (allZombies.isEmpty());
	}
	
	//Not ideal to code every level but good for now
	/**
	 * This method is the first level of the game.
	 */
	public static void level1() {	
		startLevel();
		int numOfZombies = 2;

		
		int previousCoins = coins; //The constructors below will take away coins must account for this
		//available plants keeps ONLY 1  instance of each type available in level
		allPlants.add(new SunFlower());
		allPlants.add(new VenusFlyTrap());
		coins = previousCoins;
		
		//zombies type/amount needs to be generated/level
		for (int i = 0; i < numOfZombies; i++)
			allZombies.add(new GenericZombie());
		allZombies.add(new FrankTheTank());
		allZombies.add(new BurrowingBailey());
		
		for(Plant plant: allPlants)
			plant.makeAvailable();
	}
	
	
	//function should be called on every level
	/**
	 * This method initialize the plant and zombie array.
	 */
	public static void startLevel() {
		//something to clean the board here;
		allZombies = new ArrayList<Zombie>();
		allPlants = new ArrayList<Plant>();
	}
	
	//Checks to make sure player can play at least 1 plant
	/**
	 * This method checks that a player has a plant they can play.
	 * @return A boolean, true if the player can affod a plant or false if there if they are all too expensive.
	 */
	public static boolean plantAffordable() {
		for (Plant plant: allPlants) {
			if (plant.isAffordable())
				return true;
		}
		return false;		
	}
	
	public static ArrayList<Zombie> getAllZombies(){
		return allZombies;
	}
	
	public static void setAllZombies(ArrayList<Zombie> zombies) {
		allZombies = zombies;
	}
	
}
