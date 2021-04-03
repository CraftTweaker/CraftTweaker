package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.MinecartUpdateEvent")
@ZenRegister
public interface MinecartUpdateEvent extends IMinecartEvent, IEventPositionable {
    
}
