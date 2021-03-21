package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.MinecartUpdateEvent")
@ZenRegister
public interface MinecartUpdateEvent extends IMinecartEvent, IEventPositionable {
    
}
