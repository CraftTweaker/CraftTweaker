package minetweaker;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.item.IItemDefinition;

/**
 * Platform functions are a set of internal functions implemented by the
 * platform intended to communicate between API and implementation.
 * 
 * @author Stan Hebben
 */
public interface IPlatformFunctions {
	/**
	 * Converts a string into a chat message.
	 * 
	 * @param message message string
	 * @return chat message
	 */
	public IChatMessage getMessage(String message);

	/**
	 * Distributes the given script data to all connected clients. Automatically
	 * reloads the scripts from this data.
	 * 
	 * @param data data to be distributed
	 */
	public void distributeScripts(byte[] data);

	/**
	 * Finds the item definition with the given ID.
	 * 
	 * @param id item ID
	 * @return resulting item definition
	 */
	public IItemDefinition getItemDefinition(int id);
}
