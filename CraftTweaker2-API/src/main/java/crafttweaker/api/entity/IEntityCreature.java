package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityCreature")
@ZenRegister
public interface IEntityCreature extends IEntityLiving {

    @ZenGetter
    boolean hasPath();
    
    @ZenGetter
    boolean isWithinHomeDistance();
    
    @ZenMethod
    boolean isPositionWithinHomeDistance(IBlockPos pos);
    
    @ZenMethod
    void setHomePositionAndDistance(IBlockPos pos, int distance);
    
    @ZenGetter("homePosition")
    IBlockPos getHomePosition();
    
    @ZenGetter("maximumHomeDistance")
    float getMaximumHomeDistance();
    
    @ZenMethod
    void detachHome();
    
    @ZenGetter
    boolean hasHome();
}
