package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerBonemealEvent")
@ZenRegister
public interface PlayerBonemealEvent extends IEventCancelable, PlayerEvent {
    
    
    @ZenMethod
    void process();
    
    
    @ZenGetter("processed")
    boolean isProcessed();
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("x")
    int getX();
    
    @ZenGetter("y")
    int getY();
    
    @ZenGetter("z")
    int getZ();
    
    @ZenGetter("block")
    IBlock getBlock();
    
    @ZenGetter("blockState")
    IBlockState getBlockState();
    
    @ZenGetter("blockPos")
    IBlockPos getBlockPos();
    
    @ZenGetter("dimension")
    int getDimension();
}
