package minetweaker.api.block;

import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * Block interface. Used to interact with blocks in the world.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.block.IBlock")
public interface IBlock extends IBlockPattern {
	/**
	 * Gets the block definition.
	 * 
	 * @return block definition
	 */
	@ZenGetter("definition")
	public IBlockDefinition getDefinition();

	@ZenGetter("meta")
	public int getMeta();

	@ZenGetter("data")
	public IData getTileData();
}
