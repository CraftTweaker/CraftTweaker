package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.WorldTickEvent")
@ZenRegister
public interface WorldTickEvent extends ITickEvent, IWorldEvent {

}