import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	static Scanner reader = new Scanner(System.in); // need to declare here because it must stay open most of the game
													// and player
	// turn must have access. when a scanner is closed so is it's input stream.

	public static void main(String[] args) {
		startGame();
	}

	/*
	 * This method asks if the player wants to restart the game
	 */
	public static void reStart() {
		char reply;
		do {
			System.out.println("Would you like to Re-Start the Level? y/n");
			reply = reader.next().charAt(0);
		} while (reply != 'y' && reply != 'n');

		if (reply == 'y') {
			startGame();
		} else {
			System.out.println("GAME OVER----------------------------------------------------");
			System.exit(0);
		}
	}

	/*
	 * This method starts the game
	 */
	public static void startGame() {
		System.out.println("At any point in the game enter 'h' for game options\n");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Level.level1();
		GUI gui = new GUI();

		while (true) {
			playerTurn();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println();

			// Call this multiple times if you want the zombies to move multiple space /
			// more to spawn at a time
			boardTurn();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			prepareNewTurn();

			// If Player Loses the Level
			if (GUI.endLevel()) {
				System.out.println("LOST LEVEL!-------------------------------------------------------" + "\n");
				reStart();
			}

			// If Player Wins the Level
			if (Level.allZombies.isEmpty() && !gui.zombiesLeft()) {
				System.out.print("WON LEVEL!----------------------------------------------------------" + "\n");
				reStart();
			}
		}
	}

	public static void boardTurn() {
		ArrayList<Zombie> zombiesOnBoard = new ArrayList<Zombie>();
		ArrayList<Plant> plantsOnBoard = new ArrayList<Plant>(); // These two arraylists are defined to avoid concurrent
																	// modification exception. Can't iterate through
		// zombies and plants at the same time in case one dies.
		for (GridObject gridObject : GUI.gridObjects) {
			if (gridObject instanceof Plant)
				plantsOnBoard.add((Plant) gridObject);
		}

		for (GridObject gridObject : GUI.gridObjects) {
			if (gridObject instanceof Zombie)
				zombiesOnBoard.add((Zombie) gridObject);
		} // This is just to check if zombies are empty at start. Array will need to be
			// reconstructed for the zombies turn in case one dies.

		if (!zombiesOnBoard.isEmpty()) { // This should be checked elsewhere instead of the set up above
			System.out.println("Time for the attacks");
			System.out.println();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Plant plant : plantsOnBoard) { // Plants attack first. Need to iterate through twice in case a zombie
												// dies.
				plant.go();
			}
			zombiesOnBoard = new ArrayList<Zombie>();
			for (GridObject gridObject : GUI.gridObjects) {
				if (gridObject instanceof Zombie)
					zombiesOnBoard.add((Zombie) gridObject);
			} // Reconstruct array in case a zombie was killed by a plant

			for (Zombie zombie : zombiesOnBoard) { // Plants attack first. Need to iterate through twice in case a
													// zombie dies.
				zombie.go();
			}
			GUI.printGrid();
		}
		spawnZombies();
	}

	public static void playerTurn() {
		while (true) {
			Level.printAllPlants();
			System.out.println();
			System.out.println("You have " + Level.coins + " coins");
			System.out.println();

			if (!(plantAvailable()) || !(plantAffordable())) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!(plantAvailable()))
					System.out.println("There are no available plants. You must forfeit your turn....");
				else
					System.out.println("You cannot afford any plants. You must forfeit your turn");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}

			System.out.print("Pick a Plant: ");
			String pChoice = reader.next();
			if (pChoice.equals("h"))
				printHelp();
			Plant plantChoice = createPlant(pChoice);
			while (plantChoice == null) {
				System.out.print("Pick a Plant: ");
				pChoice = reader.next();
				if (pChoice.equals("h"))
					printHelp();
				else
					plantChoice = createPlant(pChoice);
				System.out.println();
			}

			boolean notAllowedLocation = true;
			String sxPos = "";
			String syPos = "";

			// loop checks if the location specified by the player is allowed
			while (notAllowedLocation) {
				System.out.print("Pick Position: ");
				String location = reader.next();
				if (location.length() == 2) {
					if ((location.charAt(0) >= 'A' && location.charAt(0) <= 'G')
							&& ((Character.getNumericValue(location.charAt(1)) >= 1)
									&& (Character.getNumericValue(location.charAt(1)) <= 4))) {
						// check if entered column (Letter) is in between A and G
						// check if entered row (number) is in between 1 and 4
						sxPos += location.charAt(0);
						syPos += location.charAt(1);
						notAllowedLocation = false;
					}
				}
			}

			if (GUI.isEmpty(GUI.getGridX(sxPos), GUI.getGridY(syPos))) {
				GUI.placePlant(plantChoice, GUI.getGridX(sxPos), GUI.getGridY(syPos));
				plantChoice.resetTime(); // Count down to use this plant restarts
				break;
			}
			// BUG HERE!!!!! Coins are not returned to player and chosen plant's timer has
			// been reset
			// Solution1 allow player to reset only XY values (easier)
			// Solution2 somehow create plants only after XY is chosen then allow player to
			// completely reset choice (preferred)
			System.out.println("You may only place your plant on an available space. Please try again.");
		}

	}

	private static void spawnZombies() {
		if (Level.allZombies.isEmpty())
			return;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Zombies are spawning....");
		System.out.println();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int yPos = ThreadLocalRandom.current().nextInt(0, 3);
		int randZombie = ThreadLocalRandom.current().nextInt(0, Level.allZombies.size()); // zombies are filled in order
																							// of type need to pick
																							// randomly
		Zombie zombie = Level.allZombies.remove(randZombie); // Unlike plant this contains all zombies for the level.
																// Must remove to place on board.

		if (GUI.isEmpty(GUI.GRID_WIDTH - 1, yPos)) // Just doesn't happen if the intended position is occupied. This
													// should be fixed
			GUI.placeZombie(zombie, GUI.GRID_WIDTH - 1, yPos);
	}

	private static Plant createPlant(String choice) {
		for (Plant plant : Level.allPlants) {
			if (choice.equals(plant.toShortString())) {
				if (!(plant.isAvailable())) {
					System.out.println("This plant is not available yet");
					return null;
				} else if (!(plant.isAffordable())) {
					System.out.println("You cannot afford this plant");
					return null;
				} else {
					if (choice.equals("S"))

						return new Sunflower();
					else if (choice.equals("V"))
						return new VenusFlyTrap();
					/// PUT MORE PLANTS HERE
					else
						System.out.println("MUST DEFINE THIS PLANT IN CREATE FUNCTION");
				}
			}
		}
		System.out.println("Not a plant name. Try again: ");
		return null;
	}

	// Seems redundant atm but thinking there may be more actions to be performed at
	// the end of board turn later.
	private static void prepareNewTurn() {
		for (Plant plant : Level.allPlants) // This function just decreases the wait time for all plant types. Does this
											// to one of each plant
			plant.newTurn();
		for (GridObject plant : GUI.gridObjects) { // Possibility to extend this to other objects and call a method they
													// all inherit instead of instance of.
			if (plant instanceof Sunflower) {
				Level.coins += Sunflower.COIN_BONUS;
			}

		}
	}

	private static void printHelp() {
		System.out.println("THIS IS THE HELP TEXT, WILL SHOW YOU PLANT ZOMBIE TYPES ETC");
		System.out.println();
	}

	private static boolean plantAvailable() {
		for (Plant plant : Level.allPlants) {
			if (plant.isAvailable()) {
				return true;
			}
		}
		return false;
	}

	private static boolean plantAffordable() {
		for (Plant plant : Level.allPlants) {
			if (plant.isAffordable()) {
				return true;
			}
		}
		return false;
	}
}
