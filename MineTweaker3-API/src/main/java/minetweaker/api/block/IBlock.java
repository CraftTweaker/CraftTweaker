package minetweaker.api.block;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * Block interface. Used to interact with blocks in the world.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.block.IBlock")
public interface IBlock {
	/**
	 * Gets the block definition.
	 * 
	 * @return block definition
	 */
	@ZenGetter("definition")
	public IBlockDefinition getDefinition();
}
