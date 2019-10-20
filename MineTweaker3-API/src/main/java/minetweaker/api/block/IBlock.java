package minetweaker.api.block;

import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.*;

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
    IBlockDefinition getDefinition();

    @ZenGetter("meta")
    int getMeta();

    @ZenGetter("data")
    IData getTileData();
}
