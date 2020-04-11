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
@ZenClass("crafttweaker.event.PlayerBonemealEvent")
@ZenRegister
public interface PlayerBonemealEvent extends IEventCancelable, IPlayerEvent, IProcessableEvent, IEventPositionable {

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("block")
    IBlock getBlock();

    @ZenGetter("blockState")
    IBlockState getBlockState();

    @ZenGetter("dimension")
    int getDimension();

    @ZenGetter("item")
    IItemStack getItem();
}
