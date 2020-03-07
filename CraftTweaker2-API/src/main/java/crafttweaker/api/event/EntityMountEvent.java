package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.EntityMountEvent")
@ZenRegister
public interface EntityMountEvent extends IEntityEvent {
  @ZenGetter("mountingEntity")
  IEntity getMountingEntity();

  @ZenGetter("mountedEntity")
  IEntity getMountedEntity();

  @ZenGetter("isMounting")
  boolean isMounting();

  @ZenGetter("isDismounting")
  boolean isDismounting();

  @ZenGetter("world")
  IWorld getWorld();
}
