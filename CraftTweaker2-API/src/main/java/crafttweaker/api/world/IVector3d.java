package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IVector3d")
@ZenRegister
public interface IVector3d {
    
    @ZenGetter("x")
    double getX();
    
    @ZenGetter("y")
    double getY();
    
    @ZenGetter("z")
    double getZ();
    
    @ZenGetter("normalized")
    IVector3d getNormalized();
    
    @ZenMethod
    double dotProduct(IVector3d other);
    
    @ZenMethod
    IVector3d crossProduct(IVector3d other);
    
    @ZenMethod
    IVector3d subtract(IVector3d other);
    
    @ZenMethod
    IVector3d add(IVector3d other);
    
    @ZenMethod
    double distanceTo(IVector3d other);
    
    @ZenMethod
    IVector3d scale(double factor);
    
    
    Object getInternal();
}
