package minetweaker.api.game;

import java.util.List;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
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
	 * Retrieves the items in this game.
	 * 
	 * @return game items
	 */
	@ZenGetter("items")
	public List<IItemDefinition> getItems();
	
	/**
	 * Retrieves the liquids in this game.
	 * 
	 * @return game liquids
	 */
	@ZenGetter("liquids")
	public List<ILiquidDefinition> getLiquids();
}
