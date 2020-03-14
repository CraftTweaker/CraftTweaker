package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.BlockFarmlandTrampleEvent")
@ZenRegister
public interface BlockFarmlandTrampleEvent extends IBlockEvent, IEventCancelable {
  @ZenGetter("entity")
  IEntity getEntity();

  @ZenGetter("fallDistance")
  float getFallDistance();
}
