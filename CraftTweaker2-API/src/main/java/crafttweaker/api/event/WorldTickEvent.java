package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.WorldTickEvent")
@ZenRegister
public interface WorldTickEvent extends ITickEvent {
    
    @ZenGetter("world")
    IWorld getWorld();
    
}
