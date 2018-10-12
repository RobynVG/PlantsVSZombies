
public class GenericZombie extends Zombie{
	
	public GenericZombie() {
		strength = 100;
	}
	
	@Override
	public String toString() {
		return "[ g ]";
	}
	
	@Override
	public void attack(Plant plant) {
		plant.loseHealth(strength);
	}
}
