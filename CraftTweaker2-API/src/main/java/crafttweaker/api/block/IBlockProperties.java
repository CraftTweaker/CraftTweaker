package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.block.IBlockProperties")
@ZenRegister
public interface IBlockProperties {
    
    @ZenMethod
    int getLightValue(IBlockAccess access, IBlockPos pos);
    
    @ZenGetter("canProvidePower")
    boolean canProvidePower();
    
    @ZenMethod
    int getWeakPower(IBlockAccess access, IBlockPos pos, IFacing facing);
    @ZenMethod
    int getComparatorInputOverride(IWorld world, IBlockPos pos);
    
    @ZenGetter("mobilityFlag")
    IMobilityFlag getMobilityFlag();
    
    @ZenGetter("material")
    IMaterial getMaterial();
    
    @ZenMethod
    boolean canEntitySpawn(IEntity entity);
    
    @ZenGetter("causesSuffocation")
    boolean causesSuffocation();
    
    @ZenMethod
    boolean doesSideBlockRendering(IBlockAccess access, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    IBlockState getActualState(IBlockAccess access, IBlockPos pos);
    
    @ZenMethod
    float getBlockHardness(IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getBoundingBox(IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getCollosionBoundingBox(IWorld world, IBlockPos pos);
    
    @ZenMethod
    int getLightOpacity(IBlockAccess access, IBlockPos pos);
    @ZenMethod
    float getPlayerRelativeBlockHardness(IPlayer player, IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getSelectedBoundingBox(IWorld world, IBlockPos pos);
    
    @ZenMethod
    int getStrongPower(IBlockAccess access, IBlockPos pos, IFacing facing);
    
    @ZenGetter("hasCustomBreakingProgress")
    boolean hasCustomBreakingProgress();
    
    @ZenGetter("blockNormalCube")
    boolean isBlockNormalCube();
    
    @ZenGetter("fullBlock")
    boolean isFullBlock();
    
    @ZenGetter("fullCube")
    boolean isFullCube();
    
    @ZenGetter("normalCube")
    boolean isNormalCube();
    
    @ZenGetter("opaqueCube")
    boolean isOpaqueCube();
    
    @ZenMethod
    boolean isSideSolid(IBlockAccess access, IBlockPos pos, IFacing facing);
    
    @ZenGetter("useNeighborBrightness")
    boolean useNeighborBrightness();
    
    Object getInternal();
}
