package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.IEventPositionable")
@ZenRegister
public interface IEventPositionable {
    
    @ZenGetter("position")
    IBlockPos getPosition();
    
    @ZenGetter("x")
    default int getX() {
        return getPosition().getX();
    }
    
    @ZenGetter("y")
    default int getY() {
        return getPosition().getY();
    }
    
    @ZenGetter("z")
    default int getZ() {
        return getPosition().getZ();
    }
}
