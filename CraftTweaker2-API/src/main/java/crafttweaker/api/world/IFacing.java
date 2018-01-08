package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IFacing")
@ZenRegister
public interface IFacing {
    
    @ZenMethod
    @ZenGetter("opposite")
    IFacing opposite();
    
    @ZenGetter("name")
    @ZenMethod
    String getName();
    
    Object getInternal();
    
    @ZenOperator(OperatorType.COMPARE)
    @ZenMethod
    int compare(IFacing other);
    
    @ZenMethod
    @ZenGetter("rotateY")
    IFacing rotateY();
}
