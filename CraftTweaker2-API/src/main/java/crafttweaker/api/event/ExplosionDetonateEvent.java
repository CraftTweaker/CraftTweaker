package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.ExplosionDetonateEvent")
@ZenRegister
public interface ExplosionDetonateEvent extends IExplosionEvent {
  @ZenGetter("affectedEntities")
  @ZenMethod
  IEntity[] getAffectedEntities();

  @ZenGetter("affectedPositions")
  @ZenMethod
  IBlockPos[] getAffectedPositions();
}
