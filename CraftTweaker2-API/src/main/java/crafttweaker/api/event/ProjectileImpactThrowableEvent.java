package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ProjectileImpactThrowableEvent")
@ZenRegister
public interface ProjectileImpactThrowableEvent extends IProjectileImpactEvent, IEventCancelable {
    @ZenGetter("throwable")
    IEntity getThrowable();

    @ZenGetter("thrower")
    IEntityLivingBase getThrower();
}
