package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseHoeEvent")
@ZenRegister
public interface PlayerUseHoeEvent extends IEventCancelable, PlayerEvent {
    
    @ZenMethod
    void process();
    
    @ZenGetter("processed")
    boolean isProcessed();
    
    @ZenGetter("item")
    IItemStack getItem();
    
    @ZenGetter("world")
    IWorld getBlocks();
    
    @ZenGetter("x")
    int getX();
    
    @ZenGetter("y")
    int getY();
    
    @ZenGetter("z")
    int getZ();
    
    @ZenGetter("dimension")
    int getDimension();
    
    @ZenGetter("block")
    IBlock getBlock();
    
    IBlockState getBlockState();
    
    @ZenGetter("position")
    IBlockPos getPosition();
}
