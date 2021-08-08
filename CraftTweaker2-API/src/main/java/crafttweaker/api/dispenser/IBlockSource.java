package crafttweaker.api.dispenser;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.dispenser.IBlockSource")
public interface IBlockSource {
    @ZenGetter("x")
    double getX();

    @ZenGetter("y")
    double getY();

    @ZenGetter("z")
    double getZ();

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("blockState")
    IBlockState getBlockState();

    @ZenGetter("pos")
    IBlockPos getPos();

    @ZenGetter("facing")
    IFacing getFacing();

    Object getInternal();
}
