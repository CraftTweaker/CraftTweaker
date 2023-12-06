package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.List;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.ExplosionDetonateEvent")
@ZenRegister
public interface ExplosionDetonateEvent extends IExplosionEvent {
    @ZenGetter("affectedEntities")
    @ZenMethod
    List<IEntity> getAffectedEntities();

    @ZenGetter("affectedPositions")
    @ZenMethod
    List<IBlockPos> getAffectedPositions();

    @ZenSetter("affectedEntities")
    @ZenMethod
    void setAffectedEntities(List<IEntity> entities);

    @ZenSetter("affectedPositions")
    @ZenMethod
    void setAffectedPositions(List<IBlockPos> positions);

}
