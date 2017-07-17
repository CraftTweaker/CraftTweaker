package crafttweaker.api.game;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.world.IBiome;
import stanhebben.zenscript.annotations.*;

import java.util.*;

/**
 * Game interface. Used to obtain general game information.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.game.IGame")
@ZenRegister
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
     * Retrieves the potions in this game.
     *
     * @return game potions
     */
    @ZenGetter("potions")
    List<IPotion> getPotions();
    
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
    
}
