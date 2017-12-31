package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.util.Position3f")
@ZenRegister
public interface IPosition3f {

    @ZenGetter("x")
    @ZenMethod
    float getX();

    @ZenSetter("x")
    @ZenMethod
    void setX(float x);

    @ZenGetter("y")
    @ZenMethod
    float getY();

    @ZenSetter("y")
    @ZenMethod
    void setY(float y);

    @ZenGetter("z")
    @ZenMethod
    float getZ();

    @ZenSetter("z")
    @ZenMethod
    void setZ(float z);

    @ZenMethod
    @ZenCaster
    IBlockPos asBlockPos();
}
