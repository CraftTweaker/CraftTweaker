/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

/**
 * This class is similar to an ArrayList, but is optimized for integers.
 *
 * @author Stan Hebben
 */
public class ArrayListI {
	private int[] data;
	private int size;

	/**
	 * Creates a new, empty ArrayList of integers.
	 */
	public ArrayListI() {
		data = new int[8];
	}

	/**
	 * Adds a value.
	 *
	 * @param value integer value
	 */
	public void add(int value) {
		if (size == data.length)
			expand();
		data[size++] = value;
	}

	/**
	 * Gets the value at the specified position.
	 *
	 * @param index position
	 * @return value at this position
	 */
	public int get(int index) {
		if (index >= size)
			throw new ArrayIndexOutOfBoundsException();
		return data[index];
	}

	/**
	 * Gets the size of this ArrayList.
	 *
	 * @return size
	 */
	public int size() {
		return size;
	}

	// //////////////////
	// Private methods
	// //////////////////

	/**
	 * Doubles the size of this array.
	 */
	private void expand() {
		int[] newdata = new int[data.length * 2];
		System.arraycopy(data, 0, newdata, 0, size);
		data = newdata;
	}

	public int[] toArray() {
		int[] result = new int[size];
		System.arraycopy(data, 0, result, 0, size);
		return result;
	}
}
