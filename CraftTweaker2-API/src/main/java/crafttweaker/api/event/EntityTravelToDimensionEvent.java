package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.EntityTravelToDimensionEvent")
@ZenRegister
public interface EntityTravelToDimensionEvent extends IEntityEvent, IEventCancelable {
  @ZenGetter("dimension")
  int getDimension();
}
