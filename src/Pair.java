/**
 * A generic class with a bounded type parameter representing a pair 
 *  
 * @author  COSC 311
 * @version (9-12-23)
 */


public class Pair <T extends Comparable<T>> implements Comparable<Pair<T>>{
	private T first;
	private T second;
	
	// constructors
	/**
     * Construct a pair and set elements to null
     * @param none
     */
	public Pair() {
		first = second = null;
	}
	
	/**
     * Construct a pair with the given data values
     * @param one - The data value for first
     * @param two - The data value for second
     */
	public Pair (T one , T two) {
		first = one;
		second = two;
	}
	
	// getters and setters
	/**
     * Assigns a new value to the first element of this pair
     * @param other a value for the first element
     * @returns none
     */
	public void setFirst (T other) {
		first = other;
	}
	
	/**
     * Assigns a new value to the second element of this pair
     * @param other a value for the second element
     * @returns none
     */
	public void setSecond (T other) {
		second = other;
	}
	
	/**
     * Returns the first element of this pair
     * @param none
     * @returns the first element of this pair
     */
	public T getFirst () {
		return first;
	}
	
	/**
     * Returns the second element of this pair
     * @param none
     * @returns the second element of this pair
     */
	public T getSecond () {
		return second;
	}
	

	/**
     * Returns a string representing a pair object
     * @param none
     * @returns a string representing a pair
     */
	public String toString() {
		return "(" +first.toString() + "," + second.toString() + ")";
		
	}
	
	/**
     * Compares two pair objects based on the value of their first element
     * @param other a pair object
     * @returns 0 if first elements are the same, -1 if first < other.first, and
     *          1 if first > other.first
     */
	public int compareTo(Pair<T> other) {
		if (first.compareTo(other.first) < 0)
			return -1;
		else if (first.compareTo(other.first) >0)
			return 1;
		else
			return 0;
	}
	
	
	/**
     * Determines whether or not this pair and other pair are identical
     * @param other a pair object
     * @returns true if two pair objects are the same, otherwise returns false
     */
	public boolean equals (Object other) {
		if (other == null) return false;
		else if (getClass() != other.getClass()) return false;
		else {
			Pair <T> otherPair = (Pair<T>) other;
			return first.equals(otherPair.first) && second.equals(otherPair.second);
		}
		
	}
	
	
	/**
     * Returns the largest element of a pair object
     * @param none
     * @returns the largest element of a pair 
     */
	public T max() {
		if (first.compareTo(second)>=0)
			return first;
		else 
			return second;
			
	}
}



