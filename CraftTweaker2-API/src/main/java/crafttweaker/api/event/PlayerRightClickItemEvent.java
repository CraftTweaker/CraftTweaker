package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.PlayerRightClickItemEvent")
@ZenRegister
public interface PlayerRightClickItemEvent extends PlayerInteractEvent, IEventCancelable, IHasCancellationResult {
}
