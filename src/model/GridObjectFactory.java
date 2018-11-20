package model;

public class GridObjectFactory {

	public static GridObject createNewGridObject(String s) {
		switch(s) {
		case ("SunFlower"): 	
			return new SunFlower();
		case ("VenusFlyTrap") :	
			return new VenusFlyTrap();
		case ("GenericZombie"):	
			return new GenericZombie();
		default:				
			return null;
		}
	}
}
