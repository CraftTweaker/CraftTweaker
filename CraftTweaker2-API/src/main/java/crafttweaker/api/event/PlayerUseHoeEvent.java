package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

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
