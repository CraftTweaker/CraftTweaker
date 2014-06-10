package stanhebben.zenscript;

/**
 * ZenRuntimeExceptions are thrown when an internal error occurs during
 * execution of scripts. Since ZenScript is only partially typesafe, errors
 * may occur at runtime due to impossible operations being executed.
 * 
 * @author Stan Hebben
 */
public class ZenRuntimeException extends RuntimeException {
	public ZenRuntimeException(String message) {
		super(message);
	}
}
