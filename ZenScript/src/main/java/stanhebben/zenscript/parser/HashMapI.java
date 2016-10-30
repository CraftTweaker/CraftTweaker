/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */
package stanhebben.zenscript.parser;

/**
 * A HashMap implementation which is optimized for integer keys.
 *
 * @author Stan Hebben
 * @param <T>
 */
public class HashMapI<T> {
	private int[] keys;
	private T[] values;
	private int[] next;
	private int mask;
	private int size;

	/**
	 * Creates a new HashMap for integer keys.
	 */
	public HashMapI() {
		keys = new int[16];
		values = (T[]) new Object[16];
		next = new int[16];
		mask = 15;
		size = 0;

		for (int i = 0; i < keys.length; i++) {
			keys[i] = Integer.MIN_VALUE;
		}
	}

	/**
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for the key, the old value is
	 * replaced.
	 *
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 */
	public void put(int key, T value) {
		if (size > (keys.length * 3) >> 2) {
			expand();
		}

		int index = key & mask;

		if (keys[index] == Integer.MIN_VALUE) {
			keys[index] = key;
			values[index] = value;
		} else {
			if (keys[index] == key) {
				values[index] = value;
				return;
			}
			while (next[index] != 0) {
				index = next[index] - 1;
				if (keys[index] == key) {
					values[index] = value;
					return;
				}
			}
			int ref = index;
			while (keys[index] != Integer.MIN_VALUE) {
				index++;
				if (index == keys.length)
					index = 0;
			}
			next[ref] = index + 1;

			keys[index] = key;
			values[index] = value;
		}

		size++;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this
	 *         map contains no mapping for the key
	 */
	public T get(int key) {
		int index = key & mask;
		while (keys[index] != key) {
			if (next[index] == 0) {
				return null;
			}
			index = next[index] - 1;
		}
		return values[index];
	}

	/**
	 * Returns true if this map contains a mapping for the specified key.
	 *
	 * @param key The key whose presence in this map is to be tested
	 * @return true if this map contains a mapping for the specified key.
	 */
	public boolean containsKey(int key) {
		int index = key & mask;
		while (keys[index] != key) {
			if (next[index] == 0) {
				return false;
			}
			index = next[index] - 1;
		}
		return true;
	}

	/**
	 * Returns an iterator over the keys in this HashMap. The elements are
	 * returned in no particular order.
	 *
	 * @return an iterator over the keys in this HashMap
	 */
	public IteratorI keys() {
		return new KeyIterator();
	}

	/**
	 * Returns an array with all keys in this HashMap. The elements are returned
	 * in no particular order.
	 *
	 * @return an array with all keys in this HashMap
	 */
	public int[] keysArray() {
		int[] result = new int[size];
		int ix = 0;
		for(int key : keys) {
			if(key != Integer.MIN_VALUE)
				result[ix++] = key;
		}
		return result;
	}

	// //////////////////
	// Private methods
	// //////////////////

	/**
	 * Expands the size of this array. (it doubles the size)
	 */
	private void expand() {
		int[] newKeys = new int[keys.length * 2];
		T[] newValues = (T[]) new Object[values.length * 2];
		int[] newNext = new int[next.length * 2];
		int newMask = newKeys.length - 1;

		for (int i = 0; i < newKeys.length; i++) {
			newKeys[i] = Integer.MIN_VALUE;
		}

		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == Integer.MIN_VALUE) {
				continue;
			}

			int key = keys[i];
			T value = values[i];
			int index = key & newMask;

			if (newKeys[index] == Integer.MIN_VALUE) {
				newKeys[index] = key;
				newValues[index] = value;
			} else {
				while (newNext[index] != 0) {
					index = newNext[index] - 1;
				}
				int ref = index;
				while (newKeys[index] != Integer.MIN_VALUE) {
					index = (index + 1) & newMask;
				}
				newNext[ref] = index + 1;

				newKeys[index] = key;
				newValues[index] = value;
			}
		}

		keys = newKeys;
		values = newValues;
		next = newNext;
		mask = newMask;
	}

	// ////////////////////////
	// Private inner classes
	// ////////////////////////

	private class KeyIterator implements IteratorI {
		private int i;

		public KeyIterator() {
			i = 0;
			skip();
		}

		@Override
		public boolean hasNext() {
			return i < keys.length;
		}

		@Override
		public int next() {
			int result = keys[i++];
			skip();
			return result;
		}

		private void skip() {
			while (i < keys.length && keys[i] == Integer.MIN_VALUE)
				i++;
		}
	}
}
