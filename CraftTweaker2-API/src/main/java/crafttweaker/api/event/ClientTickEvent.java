package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.ClientTickEvent")
@ZenRegister
public interface ClientTickEvent extends ITickEvent {

}
