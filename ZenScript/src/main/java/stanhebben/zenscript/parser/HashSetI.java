/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */
package stanhebben.zenscript.parser;

import java.util.Arrays;

/**
 * HashSet implementation which is optimized for integer values.
 *
 * @author Stan Hebben
 */
public class HashSetI {
    private int[] values;
    private int[] next;
    private int mask;
    private int size;

    /**
     * Creates a new HashSet for integer values.
     */
    public HashSetI() {
        values = new int[16];
        next = new int[16];
        mask = 15;
        size = 0;

        for (int i = 0; i < values.length; i++) {
            values[i] = Integer.MIN_VALUE;
        }
    }

    public HashSetI(HashSetI original) {
        values = Arrays.copyOf(original.values, original.values.length);
        next = Arrays.copyOf(original.next, original.next.length);
        mask = original.mask;
        size = original.size;
    }

    /**
     * Adds the specified value to this HashSet. If this value is already in this
     * HashSet, nothing happens.
     *
     * @param value value to be added to the HashSet
     */
    public void add(int value) {
        if (size > (values.length * 3) >> 2) {
            expand();
        }

        int index = value & mask;

        if (values[index] == Integer.MIN_VALUE) {
            values[index] = value;
        } else {
            if (values[index] == value) {
                return;
            }
            while (next[index] != 0) {
                index = next[index] - 1;
                if (values[index] == value) {
                    return;
                }
            }
            int ref = index;
            while (values[index] != Integer.MIN_VALUE) {
                index++;
                if (index == values.length) index = 0;
            }
            next[ref] = index + 1;

            values[index] = value;
        }

        size++;
    }

    /**
     * Checks if this HashSet contains the specified value.
     *
     * @param value integer value to add
     * @return true if this HashSet contains the specified value
     */
    public boolean contains(int value) {
        int index = value & mask;
        while (values[index] != value) {
            if (next[index] == 0) {
                return false;
            }
            index = next[index] - 1;
        }
        return true;
    }

    /**
     * Returns an iterator over this HashSet. The elements are returned in no
     * particular order.
     *
     * @return an iterator over this HashSet
     */
    public IteratorI iterator() {
        return new KeyIterator();
    }

    /**
     * Converts the contents of this HashSet to an integer array. The order of
     * the elements is unspecified.
     *
     * @return this HashSet as integer array
     */
    public int[] toArray() {
        int[] result = new int[size];
        int ix = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != Integer.MIN_VALUE) {
                result[ix++] = values[i];
            }
        }
        return result;
    }

    //////////////////////////
    // Private inner methods
    //////////////////////////

    /**
     * Expands the capacity of this HashSet. (double it)
     */
    private void expand() {
        int[] newKeys = new int[values.length * 2];
        int[] newNext = new int[next.length * 2];
        int newMask = newKeys.length - 1;

        for (int i = 0; i < newKeys.length; i++) {
            newKeys[i] = Integer.MIN_VALUE;
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] == Integer.MIN_VALUE) {
                continue;
            }

            int key = values[i];
            int index = key & newMask;

            if (newKeys[index] == Integer.MIN_VALUE) {
                newKeys[index] = key;
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
            }
        }

        values = newKeys;
        next = newNext;
        mask = newMask;
    }

    //////////////////////////
    // Private inner classes
    //////////////////////////

    private class KeyIterator implements IteratorI {
        private int i;

        public KeyIterator() {
            i = 0;
            skip();
        }

		@Override
        public boolean hasNext() {
            return i < values.length;
        }

		@Override
        public int next() {
            int result = values[i++];
            skip();
            return result;
        }

        private void skip() {
            while (i < values.length && values[i] == Integer.MIN_VALUE) i++;
        }
    }
}
