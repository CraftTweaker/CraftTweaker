package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.block.IBlockState")
@ZenRegister
public interface IBlockState {
    
    @ZenMethod
    @ZenGetter("block")
    IBlock getBlock();
    
    @ZenMethod
    @ZenGetter("meta")
    int getMeta();
    
    @ZenMethod
    boolean isReplaceable(IWorld world, IBlockPos blockPos);
    
    @ZenMethod
    int getLightValue(IWorld world, IBlockPos blockPos);
    
    @ZenMethod
    @ZenGetter("canProvidePower")
    boolean canProvidePower();
    
    @ZenMethod
    int getWeakPower(IWorld world, IBlockPos blockPos, IFacing facing);
    
    @ZenMethod
    int getComparatorInputOverride(IWorld world, IBlockPos blockPos);
    
    @ZenMethod
    @ZenGetter("mobilityFlag")
    IMobilityFlag getMobilityFlag();
    
    @ZenMethod
    @ZenOperator(OperatorType.COMPARE)
    int compare(IBlockState other);
    
    @ZenMethod
    @ZenGetter("material")
    IMaterial getMaterial();
    
    @ZenMethod
    boolean canEntitySpawn(IEntity entity);
    
    @ZenMethod
    @ZenGetter("causesSuffocation")
    boolean causesSuffocation();
    
    @ZenMethod
    boolean doesSideBlockRendering(IWorld world, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    IBlockState getActualState(IWorld world, IBlockPos pos);
    
    @ZenMethod
    @ZenGetter("ambientOcclusionLightValue")
    float getAmbientOcclusionLightValue();
    
    @ZenMethod
    float getBlockHardness(IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getBoundingBox(IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getCollosionBoundingBox(IWorld world, IBlockPos pos);
    
    @ZenMethod
    @ZenGetter("lightOpacy")
    int getLightOpacy();
    
    @ZenMethod
    int getLightOpacy(IWorld world, IBlockPos pos);
    
    @ZenMethod
    @ZenGetter("lightValue")
    int getLightValue();
    
    @ZenMethod
    int getPackedLightMapCoords(IWorld world, IBlockPos pos);
    
    @ZenMethod
    float getPlayerRelativeBlockHardness(IPlayer player, IWorld world, IBlockPos pos);
    
    //@ZenMethod
    //IAxisAlignedBB getSelectedBoundingBox(IWorld world, IBlockPos pos);
    
    @ZenMethod
    int getStrongPower(IWorld world, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    @ZenGetter("hasCustomBreakingProgress")
    boolean hasCustomBreakingProgress();
    
    @ZenMethod
    @ZenGetter("blockNormalCube")
    boolean isBlockNormalCube();
    
    @ZenMethod
    @ZenGetter("fullBlock")
    boolean isFullBlock();
    
    @ZenMethod
    @ZenGetter("fullCube")
    boolean isFullCube();
    
    @ZenMethod
    @ZenGetter("normalCube")
    boolean isNormalCube();
    
    @ZenMethod
    @ZenGetter("opaqueCube")
    boolean isOpaqueCube();
    
    @ZenMethod
    boolean isSideSolid(IWorld world, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    @ZenGetter("topSolid")
    boolean isTopSolid();
    
    @ZenMethod
    @ZenGetter("translucent")
    boolean isTranslucent();
    
    @ZenMethod
    boolean shouldSideBeRendered(IWorld world, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    @ZenGetter("useNeighborBrightness")
    boolean useNeighborBrightness();
    
    Object getInternal();
}
