package model;


public abstract class GridObject {
	String imageTitle;
	/**
	 * This method returns an empty string (Will be overridden by the child class).
	 */
	public String toString() {
		return "";
	}
	
	/**
	 * This method shows the entities name (Will be overridden by the child class).
	 * @return A string.
	 */
	public String toShortString() {
		return toString().substring(2,3);
	}
	
	public String getImageTitle() {
		return imageTitle;
	}
	
}