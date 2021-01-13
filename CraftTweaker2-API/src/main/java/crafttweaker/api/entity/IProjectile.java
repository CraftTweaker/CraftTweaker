package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IProjectile")
@ZenRegister
public interface IProjectile {

    @ZenMethod
    void shoot(double x, double y, double z, float velocity, float inaccuracy);
}