package crafttweaker.api.event;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.entity.IEntityThrowable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ProjectileImpactThrowableEvent")
@ZenRegister
public interface ProjectileImpactThrowableEvent extends IProjectileImpactEvent, IEventCancelable {

    @ZenGetter("throwable")
    default IEntityThrowable getThrowableNew() {
        CraftTweakerAPI.logError("Class " + getClass() + " doesn't override ProjectileImpactThrowableEvent#getThrowableNew");
        return null;
    }

    @Deprecated
    IEntity getThrowable();

    @ZenGetter("thrower")
    IEntityLivingBase getThrower();
}