package model;

public class GridObjectFactory {

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
