package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseHoeEvent")
@ZenRegister
public interface PlayerUseHoeEvent extends IEventCancelable, IPlayerEvent, IProcessableEvent, IEventPositionable {

    @ZenGetter("item")
    IItemStack getItem();

    @ZenGetter("world")
    IWorld getBlocks();

    @ZenGetter("dimension")
    int getDimension();

    @ZenGetter("block")
    IBlock getBlock();

    @ZenGetter("blockState")
    IBlockState getBlockState();
}
