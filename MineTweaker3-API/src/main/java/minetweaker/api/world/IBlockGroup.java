package minetweaker.api.world;

import minetweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.world.IBlockGroup")
public interface IBlockGroup {

    @ZenGetter("dimension")
    IDimension getDimension();

    @ZenMethod
    IBlock getBlock(int x, int y, int z);
}
