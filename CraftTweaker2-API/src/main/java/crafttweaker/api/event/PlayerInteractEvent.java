package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEvent")
@ZenRegister
public interface PlayerInteractEvent extends IEventCancelable, IPlayerEvent, IEventPositionable {

    @ZenMethod
    void damageItem(int amount);
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("block")
    IBlock getBlock();
    
    IBlockState getBlockState();
    
    @ZenGetter("dimension")
    int getDimension();
}
