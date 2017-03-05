package minetweaker.api.game;

import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.world.IBiome;
import stanhebben.zenscript.annotations.*;

import java.util.List;

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
    List<IItemDefinition> getItems();
    
    /**
     * Retrieves the block definitions in this game.
     *
     * @return block definitions
     */
    @ZenGetter("blocks")
    List<IBlockDefinition> getBlocks();
    
    /**
     * Retrieves the liquids in this game.
     *
     * @return game liquids
     */
    @ZenGetter("liquids")
    List<ILiquidDefinition> getLiquids();
    
    /**
     * Retrieves the biomes in this game.
     *
     * @return game biomes
     */
    @ZenGetter("biomes")
    List<IBiome> getBiomes();
    
    /**
     * Retrieves the entities in this game.
     *
     * @return game entities
     */
    @ZenGetter("entities")
    List<IEntityDefinition> getEntities();
    
    @ZenMethod()
    IEntityDefinition getEntity(String entityName);
    
    /**
     * Sets a localization value.
     *
     * @param key   localization key
     * @param value localization value
     */
    @ZenMethod
    void setLocalization(String key, String value);
    
    /**
     * Sets a localization value.
     *
     * @param lang  language
     * @param key   localization key
     * @param value localization value
     */
    @ZenMethod
    void setLocalization(String lang, String key, String value);
    
    /**
     * Gets a localized string.
     *
     * @param key localization key
     *
     * @return localized value
     */
    @ZenMethod
    String localize(String key);
    
    /**
     * Gets a localized string.
     *
     * @param key  localization key
     * @param lang language
     *
     * @return localized value
     */
    @ZenMethod
    String localize(String key, String lang);
    
    /**
     * Lock the game and disable reload. Recommended for distributed versions.
     * <p>
     * Once locked, reload cannot be unlocked - a game restart is required to
     * alter the scripts.
     */
    @ZenMethod
    void lock();
    
    /**
     * Returns the lock state. True if locked.
     *
     * @return lock state
     */
    @ZenGetter("locked")
    boolean isLocked();
    
    /**
     * Signals a modified script while in locked mode.
     */
    void signalLockError();
}
