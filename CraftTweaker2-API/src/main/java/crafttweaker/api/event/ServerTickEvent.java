package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.ServerTickEvent")
@ZenRegister
public interface ServerTickEvent extends ITickEvent {
    
}
