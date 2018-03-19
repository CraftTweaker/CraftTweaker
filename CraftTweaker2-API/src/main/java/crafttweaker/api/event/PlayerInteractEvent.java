package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEvent")
@ZenRegister
public interface PlayerInteractEvent extends IEventCancelable, PlayerEvent{

    @ZenMethod
    void damageItem(int amount);
    
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
    
    IBlockState getBlockState();
    
    @ZenGetter("dimension")
    int getDimension();
    
    @ZenGetter("position")
    IBlockPos getPosition();
}
