
public class GenericZombie extends Zombie{
	private int strength = 100;
	private int health = 250;
	
	
	@Override
	public String toString() {
		return "[ g ]";
	}
	
	@Override
	public void attack(Plant plant) {
		plant.loseHealth(strength);
	}
	
	public void loseHealth(int plantStrength) {
		health = health - plantStrength;
		if (health <= 0)
			GUI.remove(this);
	}
}
