package stanhebben.zenscript.parser;

import stanhebben.zenscript.util.ZenPosition;

/**
 * Represents a token in a token stream.
 *
 * @author Stan Hebben
 */
public class Token {
    
    private final ZenPosition position;
    private final String value;
    private final int type;
    
    /**
     * Constructs a new token.
     *
     * @param value    token string value
     * @param type     token type
     * @param position token position
     */
    public Token(String value, int type, ZenPosition position) {
        this.value = value;
        this.type = type;
        this.position = position;
    }
    
    public ZenPosition getPosition() {
        return position;
    }
    
    /**
     * Returns the string value of this token.
     *
     * @return token value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Returns the token type of this token.
     *
     * @return token type
     */
    public int getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return position.getLine() + ":" + position.getLineOffset() + " (" + type + ") " + value;
    }
}
