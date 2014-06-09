/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

/**
 * Thrown when an exception occurs while tokening a character stream.
 *
 * @author Stan Hebben
 */
public class TokenException extends RuntimeException {
    public TokenException(int line, int lineOffset, char value) {
        super("Invalid character at " + line + ":" + lineOffset + " - " + value);
    }
}
