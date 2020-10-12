package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.EntityJoinWorldEvent")
@ZenRegister
public interface EntityJoinWorldEvent extends IEntityEvent, IWorldEvent, IEventCancelable {

}