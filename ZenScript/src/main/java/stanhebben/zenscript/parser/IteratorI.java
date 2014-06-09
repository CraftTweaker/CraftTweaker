                                                                                 /*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

/**
 * This interface represents an iterator over integer values.
 *
 * @author Stan Hebben
 */
public interface IteratorI {
    /**
     * Returns true if the iteration has more elements.
     *
     * @return true if the iteration has more elements
     */
    public boolean hasNext();

    /**
     * Returns the next element in this iteration.
     *
     * @return the next element in this iteration
     */
    public int next();
}
