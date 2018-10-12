
public abstract class Zombie extends GridObject {
	protected int strength; //The amount of health taken off from 1 attack
	
	public void go() {
		GridObject left = GUI.toTheLeft(this);
		if (left instanceof Plant )
			attack((Plant)left);
		else if (left instanceof NullSpace)
			advance((NullSpace)left);
		//else if (left == null) //off the board plants lose!
			
		//else
			//another zombie;  //Should theoretically only happen if the zombie before this one is attacking a plant
	}
	
	//All attacks should be different -Override
	public void attack(Plant plant) {}
	
	//For now zombies only move one space -eventually this must be overridden
	public void advance(NullSpace nullSpace) {
		GUI.move(this, nullSpace);
	}

	//This class is abstract, no need for a string.
	public String toString() {
		return "";
	}
}
