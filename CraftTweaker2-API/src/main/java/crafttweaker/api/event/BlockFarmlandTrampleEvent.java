package crafttweaker.api.event;

import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
public interface BlockFarmlandTrampleEvent extends IBlockEvent, IEventCancelable {
  @ZenGetter("entity")
  IEntity getEntity();

  @ZenGetter("fallDistance")
  float getFallDistance();
}
