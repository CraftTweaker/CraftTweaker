package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityThrowable")
@ZenRegister
public interface IEntityThrowable extends IEntity {
    
    @ZenGetter("thrower")
    IEntityLivingBase getThrower();
    
	@ZenMethod
    @ZenGetter("shake")
    int throwableShake();
}