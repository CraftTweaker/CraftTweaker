package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public interface PlayerSleepInBedEvent extends PlayerEvent{
    
    @ZenGetter("x")
    int getX();
    
    @ZenGetter("y")
    int getY();
    
    @ZenGetter("z")
    int getZ();
    
    @ZenGetter("position")
    IBlockPos getPosition();
}
