package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IRayTraceResult;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ProjectileImpactEvent")
@ZenRegister
public interface ProjectileImpactEvent extends IEntityEvent {
  @ZenGetter("rayTrace")
  IRayTraceResult getRayTraceResult();
}
