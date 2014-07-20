/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import stanhebben.zenscript.ZenParsedFile;

/**
 * Thrown when an exception occurs while tokening a character stream.
 *
 * @author Stan Hebben
 */
public class TokenException extends RuntimeException {
    public TokenException(ZenParsedFile file, int line, int lineOffset, char value) {
        super("Invalid character at " + file + ":" + line + " - " + value);
		
		if (line < 0) throw new IllegalArgumentException("Line cannot be negative");
    }
}
