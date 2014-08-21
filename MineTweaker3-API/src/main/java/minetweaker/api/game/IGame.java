package minetweaker.api.game;

import java.util.List;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.world.IBiome;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

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
}
