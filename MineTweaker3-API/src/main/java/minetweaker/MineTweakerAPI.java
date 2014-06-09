package minetweaker;

import minetweaker.minecraft.recipes.IRecipeManager;
import minetweaker.minecraft.oredict.IOreDict;
import minetweaker.minecraft.recipes.IFurnaceManager;

/**
 * Provides access to the MineTweaker API.
 * 
 * An implementing platform needs to do the following:
 * - Set a logger
 * - Set the ore dictionary
 * - Set the recipe manager
 * - Set the furnace manager
 * 
 * - Register additional global symbols to the GlobalRegistry (recipes,
 *    minetweaker, oreDict, logger, as well as the official set of functions)
 * - Register native classes using the GlobalRegistry
 * - Register bracket handlers to resolve block/item/... references using the
 *    bracket syntax
 * 
 * @author Stan Hebben
 */
public class MineTweakerAPI {
	private MineTweakerAPI() {}
	
	/**
	 * The Tweaker is where you apply undoable actions. Any kind of action that
	 * reloads with the scripts should always be submitted to the tweaker.
	 */
	public static final IMineTweaker tweaker = new Tweaker();
	
	/**
	 * The logger can be used to write logging messages to the client. Error and
	 * warning messages should be relayed to admins for further handling.
	 */
	public static ILogger logger = null;
	
	/**
	 * Access point to the ore dictionary.
	 */
	public static IOreDict oreDict = null;

	/**
	 * Access point to the recipe manager.
	 */
	public static IRecipeManager recipes = null;
	
	/**
	 * Access point to the furnace manager.
	 */
	public static IFurnaceManager furnace = null;
}
