package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.entity.IEntityThrowable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ProjectileImpactThrowableEvent")
@ZenRegister
public interface ProjectileImpactThrowableEvent extends IProjectileImpactEvent, IEventCancelable {
    @ZenGetter("throwable")
    IEntityThrowable getThrowable();

    @ZenGetter("thrower")
    IEntityLivingBase getThrower();
}
