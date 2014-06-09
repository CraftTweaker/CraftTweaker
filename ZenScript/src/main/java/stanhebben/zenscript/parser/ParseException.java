/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import stanhebben.zenscript.ZenParsedFile;

/**
 *
 * @author Stan
 */
public class ParseException extends RuntimeException {
	private final Token token;
	private final String message;
	
    public ParseException(Token token, String error) {
        super(token == null ? "Error at end of file - " + error : "Error parsing line " + token.getPosition().getLine() + ":" + token.getPosition().getLineOffset() + " - " + error + " (last token: " + token.getValue() + ")");
        
        this.token = token;
        this.message = error;
    }
    
    public ZenParsedFile getFile() {
    	return token == null ? null : token.getPosition().getFile();
    }
    
    public int getLine() {
    	return token == null ? -1 : token.getPosition().getLine();
    }
    
    public int getLineOffset() {
    	return token == null ? -1 : token.getPosition().getLineOffset();
    }
    
    public String getExplanation() {
    	return message;
    }
}
