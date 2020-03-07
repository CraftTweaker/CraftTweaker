package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.ExplosionStartEvent")
@ZenRegister
public interface ExplosionStartEvent extends IExplosionEvent {
}
