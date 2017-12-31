package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.world.IBlockGroup")
@ZenRegister
public interface IBlockGroup {

    @ZenGetter("dimension")
    IDimension getDimension();

    @ZenMethod
    IBlock getBlock(int x, int y, int z);
}
