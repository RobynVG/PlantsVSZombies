import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	static Scanner reader = new Scanner(System.in);

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
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Level.level1();
		Board gui = new Board();

		while (true) {
			playerTurn();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println();

			boardTurn();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			prepareNewTurn();

			// If Player Loses the Level
			if (Board.endLevel()) {
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
		for (GridObject gridObject : Board.gridObjects) {
			if (gridObject instanceof Plant)
				plantsOnBoard.add((Plant) gridObject);
		}

		for (GridObject gridObject : Board.gridObjects) {
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
			for (GridObject gridObject : Board.gridObjects) {
				if (gridObject instanceof Zombie)
					zombiesOnBoard.add((Zombie) gridObject);
			} // Reconstruct array in case a zombie was killed by a plant

			for (Zombie zombie : zombiesOnBoard) { // Plants attack first. Need to iterate through twice in case a
													// zombie dies.
				zombie.go();
			}
			Board.printGrid();
		}
		spawnZombies();
	}

	public static void playerTurn() {
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
		}

		// Player Choice
		String reply = "";
		System.out.print("Would you like to Skip The Turn? Place a plant skip/place" + "\n");
		while (!reply.equals("skip") && !reply.equals("place") && !reply.equals("shovel")) {
			System.out.print("Choice: ");
			reply = reader.next();
		}

		// Player decides to skip the turn
		if (reply.equals("skip")) {
			return;
		} else if (reply.equals("place")) {
			Plant plantChoice = null;
			String pChoice;

			while (plantChoice == null) {
				System.out.print("Pick a Plant: ");
				pChoice = reader.next();
				plantChoice = createPlant(pChoice);
				System.out.println();
			}

			boolean notAllowedLocation = true;
			String sxPos = "";
			String syPos = "";

			// loop checks if the location specified by the player is allowed
			while (notAllowedLocation) {
				System.out.print("Place Location at COLROW, example 'A1'" + "\n" + "Pick Position: ");
				String location = reader.next();
				if (location.length() == 2) {
					if ((location.charAt(0) >= 'A' && location.charAt(0) <= 'F')
							&& ((Character.getNumericValue(location.charAt(1)) >= 1)
									&& (Character.getNumericValue(location.charAt(1)) <= 4))) {
						// check if entered column (Letter) is in between A and F
						// check if entered row (number) is in between 1 and 4
						sxPos += location.charAt(0);
						syPos += location.charAt(1);
						notAllowedLocation = false;
					}
				}
			}

			// If the board location is empty specified by the Player, place the plant
			if (Board.isEmpty(Board.getGridX(sxPos), Board.getGridY(syPos))) {
				Board.placePlant(plantChoice, Board.getGridX(sxPos), Board.getGridY(syPos));
				plantChoice.resetTime(); // Count down to use this plant restarts
			} else
				// If board location not empty, cannot place plant, display message
				System.out.println("You may only place your plant on an available space. Please try again." + "\n");
		} else if (reply.equals("shovel")) {

			boolean notAllowedLocation = true;
			String sxPos = "";
			String syPos = "";
			String location = "";
			// loop checks if the location specified by the player is allowed
			while (notAllowedLocation || !location.equals("exit")) {
				System.out.print("Shovel Location at COLROW, example 'A1'" + "\n" + "Pick Position: ");
				location = reader.next();
				if (location.length() == 2) {
					if ((location.charAt(0) >= 'A' && location.charAt(0) <= 'F')
							&& ((Character.getNumericValue(location.charAt(1)) >= 1)
									&& (Character.getNumericValue(location.charAt(1)) <= 4))) {
						// check if entered column (Letter) is in between A and F
						// check if entered row (number) is in between 1 and 4
						sxPos += location.charAt(0);
						syPos += location.charAt(1);

						// If the board location is empty specified by the Player, place the plant
						if (Board.isPlant(Board.getGridX(sxPos), Board.getGridY(syPos))) {
							Board.removePlant(Board.getGridX(sxPos), Board.getGridY(syPos));
							System.out.println("Shoveled plant at location : " + location);
							return;
						}
					}
				}
			}
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

		if (Board.isEmpty(Board.GRID_WIDTH - 1, yPos)) // Just doesn't happen if the intended position is occupied. This
														// should be fixed
			Board.placeZombie(zombie, Board.GRID_WIDTH - 1, yPos);
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

						return new SunFlower();
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
		for (GridObject plant : Board.gridObjects) { // Possibility to extend this to other objects and call a method
														// they
														// all inherit instead of instance of.
			if (plant instanceof SunFlower) {
				Level.coins += SunFlower.COIN_BONUS;
			}

		}
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
