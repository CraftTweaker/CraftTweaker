package crafttweaker.mc1120.block;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.*;
import net.minecraft.util.math.RayTraceResult;

public class MCRayTraceResult implements IRayTraceResult {
    
    private final RayTraceResult traceResult;
    public MCRayTraceResult(RayTraceResult traceResult) {
        this.traceResult = traceResult;
    }
    
    
    @Override
    public boolean isMiss() {
        return traceResult.typeOfHit == RayTraceResult.Type.MISS;
    }
    
    @Override
    public boolean isEntity() {
        return traceResult.typeOfHit == RayTraceResult.Type.ENTITY;
    }
    
    @Override
    public boolean isBlock() {
        return traceResult.typeOfHit == RayTraceResult.Type.BLOCK;
    }
    
    @Override
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(traceResult.entityHit);
    }
    
    @Override
    public IBlockPos getBlockPos() {
        return CraftTweakerMC.getIBlockPos(traceResult.getBlockPos());
    }
    
    @Override
    public IFacing getSideHit() {
        return CraftTweakerMC.getIFacing(traceResult.sideHit);
    }
    
    @Override
    public RayTraceResult getInternal() {
        return traceResult;
    }
}
