package minetweaker.api.game;

import java.util.List;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.world.IBiome;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Game interface. Used to obtain general game information.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.game.IGame")
public interface IGame {
	/**
	 * Retrieves the item definitions in this game.
	 * 
	 * @return game items
	 */
	@ZenGetter("items")
	public List<IItemDefinition> getItems();

	/**
	 * Retrieves the block definitions in this game.
	 * 
	 * @return block definitions
	 */
	@ZenGetter("blocks")
	public List<IBlockDefinition> getBlocks();

	/**
	 * Retrieves the liquids in this game.
	 * 
	 * @return game liquids
	 */
	@ZenGetter("liquids")
	public List<ILiquidDefinition> getLiquids();

	/**
	 * Retrieves the biomes in this game.
	 * 
	 * @return game biomes
	 */
	@ZenGetter("biomes")
	public List<IBiome> getBiomes();

	/**
	 * Retrieves the entities in this game.
	 * 
	 * @return game entities
	 */
	@ZenGetter("entities")
	public List<IEntityDefinition> getEntities();

	/**
	 * Sets a localization value.
	 * 
	 * @param key localization key
	 * @param value localization value
	 */
	@ZenMethod
	public void setLocalization(String key, String value);

	/**
	 * Sets a localization value.
	 * 
	 * @param lang language
	 * @param key localization key
	 * @param value localization value
	 */
	@ZenMethod
	public void setLocalization(String lang, String key, String value);

	/**
	 * Gets a localized string.
	 * 
	 * @param key localization key
	 * @return localized value
	 */
	@ZenMethod
	public String localize(String key);

	/**
	 * Gets a localized string.
	 * 
	 * @param key localization key
	 * @param lang language
	 * @return localized value
	 */
	@ZenMethod
	public String localize(String key, String lang);

	/**
	 * Lock the game and disable reload. Recommended for distributed versions.
	 * 
	 * Once locked, reload cannot be unlocked - a game restart is required to
	 * alter the scripts.
	 */
	@ZenMethod
	public void lock();

	/**
	 * Returns the lock state. True if locked.
	 * 
	 * @return lock state
	 */
	@ZenGetter("locked")
	public boolean isLocked();

	/**
	 * Signals a modified script while in locked mode.
	 */
	public void signalLockError();
}
