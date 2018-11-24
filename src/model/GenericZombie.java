package model;

public class GenericZombie extends Zombie{
	public static final int STRENGTH = 100;
	public static final int FULL_HEALTH = 250;
	
	public GenericZombie() {
		super(STRENGTH, FULL_HEALTH, "GenericZombie");
	}
}