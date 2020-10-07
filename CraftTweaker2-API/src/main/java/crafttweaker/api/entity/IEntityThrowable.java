package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityThrowable")
@ZenRegister
public interface IEntityThrowable extends IEntity {

    @ZenMethod
    void shoot(double x, double y, double z, float velocity, float inaccuracy);

    @ZenMethod
    @ZenGetter("thrower")
    IEntityLivingBase getThrower();
    
	@ZenMethod
    @ZenGetter("shake")
    int getThrowableShake();
}