package crafttweaker.api.world;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IRayTraceResult")
@ZenRegister
public interface IRayTraceResult {
    
    @ZenGetter
    boolean isMiss();
    
    @ZenGetter
    boolean isEntity();
    
    @ZenGetter
    boolean isBlock();
    
    @ZenGetter("entity")
    IEntity getEntity();
    
    @ZenGetter("blockPos")
    IBlockPos getBlockPos();
    
    @ZenGetter("sideHit")
    IFacing getSideHit();
    
    Object getInternal();
}


