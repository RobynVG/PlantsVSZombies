
public abstract class GridObject {
	
	public String toString() {
		return "";
	}
	
	public String toShortString() {
		return toString().substring(2,3);
	}
	
	public boolean nextToAttacker() {
		if (this instanceof Zombie) {
			
		}
		//Only need to do for 1 to the left. Only zombie will call this
			
		else if (this instanceof Plant) {
			
		}
		return false;	
			
	}
	
	
	
}
