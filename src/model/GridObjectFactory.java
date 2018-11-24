package model;

public class GridObjectFactory {

	/**
	 * This method returns a new grid object whose type corresponds to 
	 * the paramater passed
	 * @param s
	 * @return GridObject
	 */
	public static GridObject createNewGridObject(String s) {
		switch(s) {
		case ("SunFlower"): 	
			return new SunFlower();
		case ("VenusFlyTrap"):	
			return new VenusFlyTrap();
		case ("GenericZombie"):	
			return new GenericZombie();
		case ("FrankTheTank"):
			return new FrankTheTank();
		case("BurrowingBailey"):
			return new BurrowingBailey();
		case ("Unicorn"):
			return new Unicorn();
		case("MushyMushroom"):
			return new MushyMushroom();
		case("SassySquash"):
			return new SassySquash();
		case("BaNana"):
			return new BaNana();
			
		default:				
			return null;
		}
	}
}
