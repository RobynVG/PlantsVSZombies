package model;

public class GenericZombie extends Zombie{
	private int strength = 100;
	private int health = 250;
	
	/**
	 * This method prints out a generic zombie string on the grid. 
	 * @return A String, this method returns a generic zombie string equivalent.
	 */
	@Override
	public String toString() {
		return "[ g ]";
	}
	
	
	/**
	 * This method is when a generic zombie attacks a plant.
	 */
	@Override
	public void attack(Plant plant) {
		plant.loseHealth(strength);
	}
	
	/**
	 * This method reduces the generic zombie's health when a plant has attack a generic zombie's.
	 * If the generic zombie's health is zero it is removed from the board.
	 */
	public void loseHealth(int plantStrength) {
		health = health - plantStrength;
		if (health <= 0)
			Board.remove(this);
	}
}