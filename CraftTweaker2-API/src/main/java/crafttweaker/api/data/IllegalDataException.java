package crafttweaker.api.data;

/**
 * @author Stan
 */
public class IllegalDataException extends RuntimeException {
    
    private static final long serialVersionUID = -1435092998628425304L;

    public IllegalDataException(String message) {
        super(message);
    }
}
