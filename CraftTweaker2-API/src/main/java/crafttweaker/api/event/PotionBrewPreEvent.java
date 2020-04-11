package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.PotionBrewPreEvent")
@ZenRegister
public interface PotionBrewPreEvent extends IPotionBrewEvent, IEventCancelable {
}
