package model;

public class GenericZombie extends Zombie{
	public static final int STRENGTH = 100;
	public static final int FULL_HEALTH = 250;
	
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