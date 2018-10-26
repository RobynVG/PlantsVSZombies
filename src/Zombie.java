
public abstract class Zombie extends GridObject {
	protected int strength; //The amount of health taken off a plant from 1 attack
	protected int health;
	
	public void go() {
		GridObject left = Board.toTheLeft(this);
		if (left instanceof Plant )
			attack((Plant)left);
		else if (left instanceof NullSpace)
			advance((NullSpace)left);
		//else if (left == null) //off the board plants lose!
			
		//else
			//another zombie;  //Should theoretically only happen if the zombie before this one is attacking a plant
	}
	
	public void loseHealth(int plantStrength) {
		health = health - plantStrength;
		if (health <= 0)
			Board.remove(this);
	}
	
	//All attacks should be different -Override
	public void attack(Plant plant) {}
	
	//For now zombies only move one space -eventually this must be overridden
	public void advance(NullSpace nullSpace) {
		Board.move(this, nullSpace);
	}

	//This class is abstract, no need for a string.
	public String toString() {
		return "";
	}
}
