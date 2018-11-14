package model;

public class GenericZombie extends Zombie{
	private static final int STRENGTH = 100;
	private static final int FULL_HEALTH = 250;
	
	/**
	 * This method prints out a generic zombie string on the grid. 
	 * @return A String, this method returns a generic zombie string equivalent.
	 */
	public GenericZombie() {
		super(STRENGTH, FULL_HEALTH, "GenericZombie");
	}
	
	/**
	 * This method is when a generic zombie attacks a plant.
	 */
	@Override
	public void attack(Plant plant) {
		plant.loseHealth(strength);
	}
}