package stanhebben.zenscript;

import stanhebben.zenscript.util.ZenPosition;

/**
 * Error logger. Implementations can forward errors to their own error logging
 * system.
 * 
 * @author Stan Hebben
 */
public interface IZenErrorLogger {
	/**
	 * Called when an error is detected during compilation.
	 * 
	 * @param position error position
	 * @param message error message
	 */
	public void error(ZenPosition position, String message);

	/**
	 * Called when a warning is generated during compilation.
	 * 
	 * @param position warning position
	 * @param message warning message
	 */
	public void warning(ZenPosition position, String message);
}
