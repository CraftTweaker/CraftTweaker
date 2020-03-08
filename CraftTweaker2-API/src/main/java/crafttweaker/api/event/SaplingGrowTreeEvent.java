package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.SaplingGrowTreeEvent")
@ZenRegister
public interface SaplingGrowTreeEvent extends IEventPositionable, IEventHasResult {
  // From WorldEvent
  @ZenGetter("world")
  IWorld getWorld();

  // No Random?
  // @ZenGetter("random")
  // IRandom getRandom();
}
