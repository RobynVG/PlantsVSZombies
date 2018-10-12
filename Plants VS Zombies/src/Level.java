import java.util.ArrayList;

public class Level {
	static ArrayList<Plant> allPlants; //Not actually all plants, its all plant TYPES (1 instance of each)
	static ArrayList<Zombie> allZombies; //Actually all zombies
	static int coins = 0; //0 to start but keep throughout different levels
	
	//Not ideal to code every level but good for now
	public static void level1() {	
		startLevel();

		//available plants keeps 1 instance of each type available in level
		allPlants.add(new Sunflower());
		allPlants.add(new VenusFlyTrap());
		
		//zombies type/amount needs to be generated/level
		for (int i = 0; i < 4; i++)
			allZombies.add(new GenericZombie());
		
		for(Plant plant: allPlants)
			plant.makeAvailable();
	}
	
	
	
	public static void printAllPlants() {
		System.out.println("All Plants: ");
		for (Plant plant : allPlants) {
			if (plant instanceof Sunflower) {
				String availability = "";
				if (Sunflower.currentTime == 0)
					availability = "---available";
				else
					availability = "---wait " + Sunflower.currentTime + " more turns";
				System.out.print("(S)Sunflower       " + availability);
				System.out.println();
			}
			if (plant instanceof VenusFlyTrap) {
				String availability = "";
				if (VenusFlyTrap.currentTime == 0)
					availability = "---available";
				else
					availability = "---wait " + VenusFlyTrap.currentTime + " more turns";
				System.out.print("(V)VenusFlyTrap    " + availability);
				System.out.println();
			}
		}	
	}
	
	//function should be called on every level
	public static void startLevel() {
		//something to clean the board here;
		allZombies = new ArrayList<Zombie>();
		allPlants = new ArrayList<Plant>();   //
	}
	
	//Checks to make sure player can play at least 1 plant
	public boolean plantAvailable() {
		for (Plant plant: allPlants) {
			if (plant.isAvailable())
				return true;
		}
		return false;
			
	}
	
}
