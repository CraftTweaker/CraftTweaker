package crafttweaker;

import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.item.IItemDefinition;

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
     *
     * @return chat message
     */
    IChatMessage getMessage(String message);
    
    /**
     * Finds the item definition with the given ID.
     *
     * @param id item ID
     *
     * @return resulting item definition
     */
    IItemDefinition getItemDefinition(int id);
}
