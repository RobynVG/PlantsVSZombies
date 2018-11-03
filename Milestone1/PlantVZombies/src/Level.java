import java.util.ArrayList;

public class Level {
	static ArrayList<Plant> allPlants; //Not actually all plants, its all plant TYPES (1 instance of each)
	static ArrayList<Zombie> allZombies; //Actually all zombies
	static int coins = 10; 
	
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
		
		for(Plant plant: allPlants)
			plant.makeAvailable();
	}
	
	
	/**
	 * This method prints all the plants.
	 */
	public static void printAllPlants() {
		System.out.println("All Plants: ");
		for (Plant plant : allPlants) {
			if (plant instanceof SunFlower) {
				String availability = "";
				if (SunFlower.currentTime == 0)
					availability = "---available (" + SunFlower.PRICE + " coins)";
				else
					availability = "---wait " + SunFlower.currentTime + " more turns";
				System.out.print("(S)Sunflower       " + availability);
				System.out.println();
			}
			if (plant instanceof VenusFlyTrap) {
				String availability = "";
				if (VenusFlyTrap.currentTime == 0)
					availability = "---available (" + VenusFlyTrap.PRICE + " coins)";
				else
					availability = "---wait " + VenusFlyTrap.currentTime + " more turns";
				System.out.print("(V)VenusFlyTrap    " + availability);
				System.out.println();
			}
		}	
	}
	
	//function should be called on every level
	/**
	 * This method initialize the plant and zombie array.
	 */
	public static void startLevel() {
		//something to clean the board here;
		allZombies = new ArrayList<Zombie>();
		allPlants = new ArrayList<Plant>();   //
	}
	
	//Checks to make sure player can play at least 1 plant
	/**
	 * This method checks that a player has a plant.
	 * @return A boolean, true if the player has a plant available or false if there is no available plants.
	 */
	public boolean plantAvailable() {
		for (Plant plant: allPlants) {
			if (plant.isAvailable())
				return true;
		}
		return false;
			
	}
	
}
